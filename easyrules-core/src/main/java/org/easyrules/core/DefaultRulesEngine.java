/**
 * The MIT License
 *
 *  Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.easyrules.core;

import org.easyrules.api.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * Default {@link org.easyrules.api.RulesEngine} implementation.
 *
 * This implementation handles a set of rules with unique name.
 *
 * Rules are fired according to their natural order which is priority by default.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public final class DefaultRulesEngine implements RulesEngine {

    private static final Logger LOGGER = Logger.getLogger(RulesEngine.class.getName());

    /**
     * The engine parameters
     */
    private RulesEngineParameters parameters;

    /**
     * The registered rule listeners.
     */
    private List<RuleListener> ruleListeners;

    public DefaultRulesEngine() {
        this.parameters = new RulesEngineParameters();
        this.ruleListeners = new ArrayList<>();
    }

    DefaultRulesEngine(final RulesEngineParameters parameters, final List<RuleListener> ruleListeners) {
        this.parameters = parameters;
        this.ruleListeners = ruleListeners;
        if (parameters.isSilentMode()) {
            Utils.muteLoggers();
        }
    }

    @Override
    public RulesEngineParameters getParameters() {
        return parameters;
    }

    @Override
    public List<RuleListener> getRuleListeners() {
        return ruleListeners;
    }

    @Override
    public void fire(Rules rules, Facts facts) {
        if (rules.isEmpty()) {
            LOGGER.warning("No rules registered! Nothing to apply");
            return;
        }
        sort(rules);
        logEngineParameters();
        log(rules, facts);
        apply(rules, facts);
    }

    @Override
    public Map<Rule, Boolean> check(Rules rules, Facts facts) {
        LOGGER.info("Checking rules");
        sort(rules);
        Map<Rule, Boolean> result = new HashMap<>();
        for (Rule rule : rules) {
            if (shouldBeEvaluated(rule, facts)) {
                result.put(rule, rule.evaluate(facts));
            }
        }
        return result;
    }

    private void sort(Rules rules) {
        rules.sort();
    }

    private void apply(Rules rules, Facts facts) {

        LOGGER.info("Rules evaluation started");
        for (Rule rule : rules) {

            final String name = rule.getName();
            final int priority = rule.getPriority();

            if (priority > parameters.getPriorityThreshold()) {
                LOGGER.log(Level.INFO,
                        "Rule priority threshold ({0}) exceeded at rule ''{1}'' with priority={2}, next rules will be skipped",
                        new Object[]{parameters.getPriorityThreshold(), name, priority});
                break;
            }

            if (!shouldBeEvaluated(rule, facts)) {
                LOGGER.log(Level.INFO, "Rule ''{0}'' has been skipped before being evaluated", name);
                continue;
            }
            if (rule.evaluate(facts)) {
                LOGGER.log(Level.INFO, "Rule ''{0}'' triggered", name);
                triggerListenersAfterEvaluate(rule, true);
                try {
                    triggerListenersBeforeExecute(rule, facts);
                    rule.execute(facts);
                    LOGGER.log(Level.INFO, "Rule ''{0}'' performed successfully", name);
                    triggerListenersOnSuccess(rule, facts);

                    if (parameters.isSkipOnFirstAppliedRule()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstAppliedRule is set");
                        break;
                    }
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, String.format("Rule '%s' performed with error", name), exception);
                    triggerListenersOnFailure(rule, exception, facts);
                    if (parameters.isSkipOnFirstFailedRule()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstFailedRule is set");
                        break;
                    }
                }
            } else {
                LOGGER.log(Level.INFO, "Rule ''{0}'' has been evaluated to false, it has not been executed", name);
                triggerListenersAfterEvaluate(rule, false);
                if (parameters.isSkipOnFirstNonTriggeredRule()) {
                    LOGGER.info("Next rules will be skipped since parameter skipOnFirstNonTriggeredRule is set");
                    break;
                }
            }

        }

    }

    private void triggerListenersOnFailure(final Rule rule, final Exception exception, Facts facts) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.onFailure(rule, exception, facts);
        }
    }

    private void triggerListenersOnSuccess(final Rule rule, Facts facts) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.onSuccess(rule, facts);
        }
    }

    private void triggerListenersBeforeExecute(final Rule rule, Facts facts) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.beforeExecute(rule, facts);
        }
    }

    private boolean triggerListenersBeforeEvaluate(Rule rule, Facts facts) {
        for (RuleListener ruleListener : ruleListeners) {
            if (!ruleListener.beforeEvaluate(rule, facts)) {
                return false;
            }
        }
        return true;
    }

    private void triggerListenersAfterEvaluate(Rule rule, boolean evaluationResult) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.afterEvaluate(rule, evaluationResult);
        }
    }

    private boolean shouldBeEvaluated(Rule rule, Facts facts) {
        return triggerListenersBeforeEvaluate(rule, facts);
    }

    private void logEngineParameters() {
        LOGGER.log(Level.INFO, "Engine name: {0}", parameters.getName());
        LOGGER.log(Level.INFO, "Rule priority threshold: {0}", parameters.getPriorityThreshold());
        LOGGER.log(Level.INFO, "Skip on first applied rule: {0}", parameters.isSkipOnFirstAppliedRule());
        LOGGER.log(Level.INFO, "Skip on first non triggered rule: {0}", parameters.isSkipOnFirstNonTriggeredRule());
        LOGGER.log(Level.INFO, "Skip on first failed rule: {0}", parameters.isSkipOnFirstFailedRule());
    }

    private void log(Rules rules, Facts facts) {
        LOGGER.log(Level.INFO, "Registered rules:");
        for (Rule rule : rules) {
            LOGGER.log(Level.INFO, format("Rule { name = '%s', description = '%s', priority = '%s'}",
                    rule.getName(), rule.getDescription(), rule.getPriority()));
        }

        LOGGER.log(Level.INFO, "Known facts:");
        for (Map.Entry<String, Object> fact : facts) {
            LOGGER.log(Level.INFO, format("Fact { %s : %s }", fact.getKey(), fact.getValue().toString()));
        }
    }

}

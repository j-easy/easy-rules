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
package org.jeasy.rules.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.RulesEngineListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default {@link RulesEngine} implementation.
 *
 * This implementation handles a set of rules with unique name.
 *
 * Rules are fired according to their natural order which is priority by default.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public final class DefaultRulesEngine implements RulesEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRuleListener.class);

    private RulesEngineParameters parameters;
    private List<RuleListener> ruleListeners;
    private List<RulesEngineListener> rulesEngineListeners;

    /**
     * Create a new {@link DefaultRulesEngine} with default parameters.
     */
    public DefaultRulesEngine() {
        this(new RulesEngineParameters());
    }

    /**
     * Create a new {@link DefaultRulesEngine}.
     *
     * @param parameters of the engine
     */
    public DefaultRulesEngine(final RulesEngineParameters parameters) {
        this(parameters, new ArrayList<RuleListener>());
    }

    /**
     * Create a new {@link DefaultRulesEngine}.
     *
     * @param parameters of the engine
     * @param ruleListeners to apply for each rule
     */
    public DefaultRulesEngine(final RulesEngineParameters parameters, final List<RuleListener> ruleListeners) {
        this(parameters, ruleListeners, new ArrayList<RulesEngineListener>());
    }

    /**
     * Create a new {@link DefaultRulesEngine}.
     *
     * @param parameters of the engine
     * @param ruleListeners to apply for each rule
     * @param rulesEngineListeners to apply for each rule set
     */
    public DefaultRulesEngine(final RulesEngineParameters parameters, final List<RuleListener> ruleListeners, final List<RulesEngineListener> rulesEngineListeners) {
        this.parameters = parameters;
        this.ruleListeners = new ArrayList<>();
        this.ruleListeners.add(new DefaultRuleListener());
        this.ruleListeners.addAll(ruleListeners);
        this.rulesEngineListeners = new ArrayList<>();
        this.rulesEngineListeners.add(new DefaultRulesEngineListener(parameters));
        this.rulesEngineListeners.addAll(rulesEngineListeners);
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
    public List<RulesEngineListener> getRulesEngineListeners() {
        return rulesEngineListeners;
    }

    @Override
    public void fire(Rules rules, Facts facts) {
        triggerListenersBeforeRules(rules, facts);
        doFire(rules, facts);
        triggerListenersAfterRules(rules, facts);
    }

    void doFire(Rules rules, Facts facts) {
        for (Rule rule : rules) {
            final String name = rule.getName();
            final int priority = rule.getPriority();
            if (priority > parameters.getPriorityThreshold()) {
                LOGGER.info("Rule priority threshold ({}) exceeded at rule '{}' with priority={}, next rules will be skipped",
                        parameters.getPriorityThreshold(), name, priority);
                break;
            }
            if (!shouldBeEvaluated(rule, facts)) {
                LOGGER.info("Rule '{}' has been skipped before being evaluated",
                    name);
                continue;
            }
            if (rule.evaluate(facts)) {
                triggerListenersAfterEvaluate(rule, facts, true);
                try {
                    triggerListenersBeforeExecute(rule, facts);
                    rule.execute(facts);
                    triggerListenersOnSuccess(rule, facts);
                    if (parameters.isSkipOnFirstAppliedRule()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstAppliedRule is set");
                        break;
                    }
                } catch (Exception exception) {
                    triggerListenersOnFailure(rule, exception, facts);
                    if (parameters.isSkipOnFirstFailedRule()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstFailedRule is set");
                        break;
                    }
                }
            } else {
                triggerListenersAfterEvaluate(rule, facts, false);
                if (parameters.isSkipOnFirstNonTriggeredRule()) {
                    LOGGER.info("Next rules will be skipped since parameter skipOnFirstNonTriggeredRule is set");
                    break;
                }
            }
        }
    }

    @Override
    public Map<Rule, Boolean> check(Rules rules, Facts facts) {
        triggerListenersBeforeRules(rules, facts);
        Map<Rule, Boolean> result = doCheck(rules, facts);
        triggerListenersAfterRules(rules, facts);
        return result;
    }

    private Map<Rule, Boolean> doCheck(Rules rules, Facts facts) {
        LOGGER.info("Checking rules");
        Map<Rule, Boolean> result = new HashMap<>();
        for (Rule rule : rules) {
            if (shouldBeEvaluated(rule, facts)) {
                result.put(rule, rule.evaluate(facts));
            }
        }
        return result;
    }

    private void triggerListenersOnFailure(final Rule rule, final Exception exception, Facts facts) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.onFailure(rule, facts, exception);
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

    private void triggerListenersAfterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.afterEvaluate(rule, facts, evaluationResult);
        }
    }

    private void triggerListenersBeforeRules(Rules rule, Facts facts) {
        for (RulesEngineListener rulesEngineListener : rulesEngineListeners) {
            rulesEngineListener.beforeEvaluate(rule, facts);
        }
    }

    private void triggerListenersAfterRules(Rules rule, Facts facts) {
        for (RulesEngineListener rulesEngineListener : rulesEngineListeners) {
            rulesEngineListener.afterExecute(rule, facts);
        }
    }

    private boolean shouldBeEvaluated(Rule rule, Facts facts) {
        return triggerListenersBeforeEvaluate(rule, facts);
    }

}

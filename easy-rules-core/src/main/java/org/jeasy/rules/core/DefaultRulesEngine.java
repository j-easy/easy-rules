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

    /**
     * The engine parameters
     */
    private final RulesEngineParameters parameters;

    /**
     * The registered rule listeners.
     */
    private final List<RuleListener> ruleListeners;

    /**
     * Create a new {@link DefaultRulesEngine} with default parameters.
     */
    public DefaultRulesEngine() {
        this(new RulesEngineParameters(), new ArrayList<RuleListener>());
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
     * @param ruleListeners listener of rules
     */
    public DefaultRulesEngine(final RulesEngineParameters parameters, final List<RuleListener> ruleListeners) {
        this.parameters = parameters;
        this.ruleListeners = new ArrayList<>();
        this.ruleListeners.add(new DefaultRuleListener());
        this.ruleListeners.addAll(ruleListeners);
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
    public void fire(final Rules rules, final Facts facts) {
        if (rules.isEmpty()) {
            LOGGER.warn("No rules registered! Nothing to apply");
            return;
        }
        logEngineParameters();
        log(rules);
        log(facts);
        apply(rules, facts);
    }

    @Override
    public Map<Rule, Boolean> check(final Rules rules, final Facts facts) {
        LOGGER.info("Checking rules");
        final Map<Rule, Boolean> result = new HashMap<>();
        for (final Rule rule : rules) {
            if (shouldBeEvaluated(rule, facts)) {
                result.put(rule, rule.evaluate(facts));
            }
        }
        return result;
    }

    private void apply(final Rules rules, final Facts facts) {
        LOGGER.info("Rules evaluation started");
        for (final Rule rule : rules) {
            final String name = rule.getName();
            final int priority = rule.getPriority();
            if (priority > parameters.getPriorityThreshold()) {
                LOGGER.info("Rule priority threshold ({}) exceeded at rule ''{}'' with priority={}, next rules will be skipped",
                        parameters.getPriorityThreshold(), name, priority);
                break;
            }
            if (!shouldBeEvaluated(rule, facts)) {
                LOGGER.info("Rule ''{}'' has been skipped before being evaluated",
                    name);
                continue;
            }
            final boolean evaluationResult;
            try {
                evaluationResult = rule.evaluate(facts);
            } catch (final NoSuchFactException e) {
                if (parameters.isSkipOnMissingFact()) {
                    LOGGER.info("Rule ''{}'' has been skipped due to missing fact ''{}''",
                        name, e.getMissingFact());
                    continue;
                } else {
                    triggerListenersOnEvaluateFailure(rule, e, facts);
                    if (parameters.isSkipOnFirstFailedRule()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstFailedRule is set");
                        break;
                    }
                    continue;
                }
            } catch (final RuntimeException exception) {
                triggerListenersOnEvaluateFailure(rule, exception, facts);
                if (parameters.isSkipOnFirstFailedRule()) {
                    LOGGER.info("Next rules will be skipped since parameter skipOnFirstFailedRule is set");
                    break;
                }
                continue;
            }
            if (evaluationResult) {
                triggerListenersAfterEvaluate(rule, facts, true);
                try {
                    triggerListenersBeforeExecute(rule, facts);
                    rule.execute(facts);
                    triggerListenersOnSuccess(rule, facts);
                    if (parameters.isSkipOnFirstAppliedRule()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstAppliedRule is set");
                        break;
                    }
                } catch (final Exception exception) {
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

    private void triggerListenersOnEvaluateFailure(final Rule rule, final Exception exception, final Facts facts) {
        for (final RuleListener ruleListener : ruleListeners) {
            ruleListener.onEvaluateFailure(rule, facts, exception);
        }
    }

    private void triggerListenersOnFailure(final Rule rule, final Exception exception, final Facts facts) {
        for (final RuleListener ruleListener : ruleListeners) {
            ruleListener.onFailure(rule, facts, exception);
        }
    }

    private void triggerListenersOnSuccess(final Rule rule, final Facts facts) {
        for (final RuleListener ruleListener : ruleListeners) {
            ruleListener.onSuccess(rule, facts);
        }
    }

    private void triggerListenersBeforeExecute(final Rule rule, final Facts facts) {
        for (final RuleListener ruleListener : ruleListeners) {
            ruleListener.beforeExecute(rule, facts);
        }
    }

    private boolean triggerListenersBeforeEvaluate(final Rule rule, final Facts facts) {
        for (final RuleListener ruleListener : ruleListeners) {
            if (!ruleListener.beforeEvaluate(rule, facts)) {
                return false;
            }
        }
        return true;
    }

    private void triggerListenersAfterEvaluate(final Rule rule, final Facts facts, final boolean evaluationResult) {
        for (final RuleListener ruleListener : ruleListeners) {
            ruleListener.afterEvaluate(rule, facts, evaluationResult);
        }
    }

    private boolean shouldBeEvaluated(final Rule rule, final Facts facts) {
        return triggerListenersBeforeEvaluate(rule, facts);
    }

    private void logEngineParameters() {
        LOGGER.info("Rule priority threshold: {}",
            parameters.getPriorityThreshold());
        LOGGER.info("Skip on first applied rule: {}",
            parameters.isSkipOnFirstAppliedRule());
        LOGGER.info("Skip on first non triggered rule: {}",
            parameters.isSkipOnFirstNonTriggeredRule());
        LOGGER.info("Skip on first failed rule: {}",
            parameters.isSkipOnFirstFailedRule());
        LOGGER.info("Skip on missing fact: {}",
            parameters.isSkipOnMissingFact());
    }

    private void log(final Rules rules) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Registered rules:");
            for (final Rule rule : rules) {
                LOGGER.info("Rule { name = '{}', description = '{}', priority = '{}'}",
                    rule.getName(), rule.getDescription(), rule.getPriority());
            }
        }
    }

    private void log(final Facts facts) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Known facts:");
            for (final Map.Entry<String, Object> fact : facts) {
                LOGGER.info("Fact { {} : {} }",
                    fact.getKey(),
                    fact.getValue() == null ? "null" : fact.getValue().toString()
                );
            }
        }
    }
}

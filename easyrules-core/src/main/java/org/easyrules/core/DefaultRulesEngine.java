/*
 * The MIT License
 *
 *  Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.easyrules.api.Rule;
import org.easyrules.api.RuleListener;
import org.easyrules.api.RulesEngine;
import org.easyrules.util.Utils;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
class DefaultRulesEngine implements RulesEngine {

    private static final Logger LOGGER = Logger.getLogger(RulesEngine.class.getName());

    /**
     * The rules set.
     */
    protected Set<Rule> rules;

    /**
     * The engine parameters
     */
    protected RulesEngineParameters parameters;

    /**
     * The registered rule listeners.
     */
    private List<RuleListener> ruleListeners;

    DefaultRulesEngine(final RulesEngineParameters parameters, final List<RuleListener> ruleListeners) {
        this.parameters = parameters;
        this.rules = new TreeSet<>();
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
    public void registerRule(final Object rule) {
        rules.add(asRule(rule));
    }

    @Override
    public void unregisterRule(final Object rule) {
        rules.remove(asRule(rule));
    }

    @Override
    public Set<Rule> getRules() {
        return rules;
    }

    @Override
    public List<RuleListener> getRuleListeners() {
        return ruleListeners;
    }

    @Override
    public void clearRules() {
        rules.clear();
        LOGGER.info("Rules cleared.");
    }

    @Override
    public void fireRules() {

        if (rules.isEmpty()) {
            LOGGER.warning("No rules registered! Nothing to apply");
            return;
        }

        logEngineParameters();
        sortRules();
        logRegisteredRules();
        applyRules();

    }

    private void sortRules() {
        rules = new TreeSet<>(rules);
    }

    private void applyRules() {

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

            if (rule.evaluate()) {
                LOGGER.log(Level.INFO, "Rule ''{0}'' triggered", name);
                try {
                    triggerListenersBeforeExecute(rule);
                    rule.execute();
                    LOGGER.log(Level.INFO, "Rule ''{0}'' performed successfully", name);
                    triggerListenersOnSuccess(rule);

                    if (parameters.isSkipOnFirstAppliedRule()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstAppliedRule is set");
                        break;
                    }
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, String.format("Rule '%s' performed with error", name), exception);
                    triggerListenersOnFailure(rule, exception);
                    if (parameters.isSkipOnFirstFailedRule()) {
                        LOGGER.info("Next rules will be skipped since parameter skipOnFirstFailedRule is set");
                        break;
                    }
                }
            } else {
                LOGGER.log(Level.INFO, "Rule ''{0}'' has been evaluated to false, it has not been executed", name);
            }

        }

    }

    private void triggerListenersOnFailure(final Rule rule, final Exception exception) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.onFailure(rule, exception);
        }
    }

    private void triggerListenersOnSuccess(final Rule rule) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.onSuccess(rule);
        }
    }

    private void triggerListenersBeforeExecute(final Rule rule) {
        for (RuleListener ruleListener : ruleListeners) {
            ruleListener.beforeExecute(rule);
        }
    }

    private void logEngineParameters() {
        LOGGER.log(Level.INFO, "Engine name: {0}", parameters.getName());
        LOGGER.log(Level.INFO, "Rule priority threshold: {0}", parameters.getPriorityThreshold());
        LOGGER.log(Level.INFO, "Skip on first applied rule: {0}", parameters.isSkipOnFirstAppliedRule());
        LOGGER.log(Level.INFO, "Skip on first failed rule: {0}", parameters.isSkipOnFirstFailedRule());
    }

    private void logRegisteredRules() {
        LOGGER.log(Level.INFO, "Registered rules:");
        for (Rule rule : rules) {
            LOGGER.log(Level.INFO, format("Rule { name = '%s', description = '%s', priority = '%s'}",
                    rule.getName(), rule.getDescription(), rule.getPriority()));
        }
    }

    private Rule asRule(final Object rule) {
        Rule result;
        if (Utils.getInterfaces(rule).contains(Rule.class)) {
            result = (Rule) rule;
        } else {
            result = RuleProxy.asRule(rule);
        }
        return result;
    }

    @Override
    public String toString() {
        return parameters.getName();
    }

}

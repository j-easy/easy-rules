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

import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for rules engine instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class RulesEngineBuilder {

    private final RulesEngineParameters parameters;

    private final List<RuleListener> ruleListeners;
    private final List<RulesEngineListener> rulesEngineListeners;

    /**
     * Create a new rules engine builder.
     *
     * @return a new rules engine builder
     */
    public static RulesEngineBuilder aNewRulesEngine() {
        return new RulesEngineBuilder();
    }

    private RulesEngineBuilder() {
        parameters = new RulesEngineParameters(false, false, false, RulesEngineParameters.DEFAULT_RULE_PRIORITY_THRESHOLD);
        ruleListeners = new ArrayList<>();
        rulesEngineListeners = new ArrayList<>();
    }

    /**
     * Set the <code>setSkipOnFirstAppliedRule</code> parameter.
     *
     * @param skipOnFirstAppliedRule to set
     * @return the rules engine builder
     */
    public RulesEngineBuilder withSkipOnFirstAppliedRule(final boolean skipOnFirstAppliedRule) {
        parameters.setSkipOnFirstAppliedRule(skipOnFirstAppliedRule);
        return this;
    }

    /**
     * Set the <code>skipOnFirstNonTriggeredRule</code> parameter.
     *
     * @param skipOnFirstNonTriggeredRule to set
     * @return the rules engine builder
     */
    public RulesEngineBuilder withSkipOnFirstNonTriggeredRule(final boolean skipOnFirstNonTriggeredRule) {
        parameters.setSkipOnFirstNonTriggeredRule(skipOnFirstNonTriggeredRule);
        return this;
    }

    /**
     * Set <code>skipOnFirstFailedRule</code> parameter.
     *
     * @param skipOnFirstFailedRule to set
     * @return the rules engine builder
     */
    public RulesEngineBuilder withSkipOnFirstFailedRule(final boolean skipOnFirstFailedRule) {
        parameters.setSkipOnFirstFailedRule(skipOnFirstFailedRule);
        return this;
    }

    /**
     * Set rule priority threshold.
     *
     * @param priorityThreshold to set
     * @return the rules engine builder
     */
    public RulesEngineBuilder withRulePriorityThreshold(final int priorityThreshold) {
        parameters.setPriorityThreshold(priorityThreshold);
        return this;
    }

    /**
     * Register a rule listener.
     *
     * @param ruleListener to register
     * @return the rules engine builder
     */
    public RulesEngineBuilder withRuleListener(final RuleListener ruleListener) {
        this.ruleListeners.add(ruleListener);
        return this;
    }

    /**
     * Register a rules engine listener.
     *
     * @param rulesEngineListener to register
     * @return the rules engine builder
     */
    public RulesEngineBuilder withRulesEngineListener(final RulesEngineListener rulesEngineListener) {
        this.rulesEngineListeners.add(rulesEngineListener);
        return this;
    }

    /**
     * @deprecated Silent mode is now log implementation config. Now it uses slf4j facade
     * <strong>This will be removed in v3.2</strong>
     */
    @Deprecated
    public RulesEngineBuilder withSilentMode(final boolean silentMode) {
        return this;
    }

    /**
     * Build a rules engine instance.
     *
     * @return a rules engine instance
     */
    public RulesEngine build() {
        return new DefaultRulesEngine(parameters, ruleListeners, rulesEngineListeners);
    }

}

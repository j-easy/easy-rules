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

import org.easyrules.api.RuleListener;
import org.easyrules.api.RulesEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for rules engine instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class RulesEngineBuilder {

    private RulesEngineParameters parameters;

    private List<RuleListener> ruleListeners;

    public static RulesEngineBuilder aNewRulesEngine() {
        return new RulesEngineBuilder();
    }

    private RulesEngineBuilder() {
        parameters = new RulesEngineParameters(RulesEngine.DEFAULT_NAME, false, false, RulesEngine.DEFAULT_RULE_PRIORITY_THRESHOLD, false);
        ruleListeners = new ArrayList<>();
    }

    public RulesEngineBuilder named(final String name) {
        parameters.setName(name);
        return this;
    }

    public RulesEngineBuilder withSkipOnFirstAppliedRule(final boolean skipOnFirstAppliedRule) {
        parameters.setSkipOnFirstAppliedRule(skipOnFirstAppliedRule);
        return this;
    }

    public RulesEngineBuilder withSkipOnFirstNonTriggeredRule(final boolean skipOnFirstNonTriggeredRule) {
        parameters.setSkipOnFirstNonTriggeredRule(skipOnFirstNonTriggeredRule);
        return this;
    }

    public RulesEngineBuilder withSkipOnFirstFailedRule(final boolean skipOnFirstFailedRule) {
        parameters.setSkipOnFirstFailedRule(skipOnFirstFailedRule);
        return this;
    }

    public RulesEngineBuilder withRulePriorityThreshold(final int priorityThreshold) {
        parameters.setPriorityThreshold(priorityThreshold);
        return this;
    }

    public RulesEngineBuilder withRuleListener(final RuleListener ruleListener) {
        this.ruleListeners.add(ruleListener);
        return this;
    }

    public RulesEngineBuilder withSilentMode(final boolean silentMode) {
        parameters.setSilentMode(silentMode);
        return this;
    }

    public RulesEngine build() {
        return new DefaultRulesEngine(parameters, ruleListeners);
    }

}

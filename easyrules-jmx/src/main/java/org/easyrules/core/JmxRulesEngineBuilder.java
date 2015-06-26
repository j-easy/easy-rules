/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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
import org.easyrules.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for Jmx rules engine instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class JmxRulesEngineBuilder {

    private String name;

    private boolean skipOnFirstAppliedRule;

    private boolean skipOnFirstFailedRule;

    private boolean silentMode;

    private int rulePriorityThreshold;

    private List<RuleListener> ruleListeners;

    public static JmxRulesEngineBuilder aNewJmxRulesEngine() {
        return new JmxRulesEngineBuilder();
    }

    private JmxRulesEngineBuilder() {
        skipOnFirstAppliedRule = false;
        skipOnFirstFailedRule = false;
        ruleListeners = new ArrayList<RuleListener>();
        rulePriorityThreshold = Utils.DEFAULT_RULE_PRIORITY_THRESHOLD;
        name = Utils.DEFAULT_ENGINE_NAME;
    }

    public JmxRulesEngineBuilder named(String name) {
        this.name = name;
        return this;
    }

    public JmxRulesEngineBuilder withSkipOnFirstAppliedRule(boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        return this;
    }

    public JmxRulesEngineBuilder withSkipOnFirstFailedRule(boolean skipOnFirstFailedRule) {
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
        return this;
    }

    public JmxRulesEngineBuilder withRulePriorityThreshold(int rulePriorityThreshold) {
        this.rulePriorityThreshold = rulePriorityThreshold;
        return this;
    }

    public JmxRulesEngineBuilder withRuleListener(RuleListener ruleListener) {
        this.ruleListeners.add(ruleListener);
        return this;
    }

    public JmxRulesEngineBuilder withSilentMode(boolean silentMode) {
        this.silentMode = silentMode;
        return this;
    }

    public DefaultJmxRulesEngine build() {
        return new DefaultJmxRulesEngine(name, skipOnFirstAppliedRule, skipOnFirstFailedRule, rulePriorityThreshold,
                ruleListeners, silentMode);
    }

}

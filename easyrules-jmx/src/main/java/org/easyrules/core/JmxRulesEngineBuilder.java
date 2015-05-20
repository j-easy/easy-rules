package org.easyrules.core;

import org.easyrules.api.RuleListener;
import org.easyrules.util.EasyRulesConstants;

import java.util.ArrayList;
import java.util.List;

public class JmxRulesEngineBuilder {

    private boolean skipOnFirstAppliedRule;

    private boolean skipOnFirstFailedRule;

    private boolean silentMode;

    private int rulePriorityThreshold;

    private List<RuleListener> ruleListeners;

    public static JmxRulesEngineBuilder aNewJmxRulesEngine() {
        return new JmxRulesEngineBuilder();
    }

    public JmxRulesEngineBuilder() {
        skipOnFirstAppliedRule = false;
        skipOnFirstFailedRule = false;
        ruleListeners = new ArrayList<RuleListener>();
        rulePriorityThreshold = EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD;
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
        return new DefaultJmxRulesEngine(skipOnFirstAppliedRule, skipOnFirstFailedRule, rulePriorityThreshold,
                ruleListeners, silentMode);
    }

}

package org.easyrules.core;

import org.easyrules.util.EasyRulesConstants;

public class JmxRulesEngineBuilder {

    private boolean skipOnFirstAppliedRule;

    private int rulePriorityThreshold;

    public static JmxRulesEngineBuilder aNewJmxRulesEngine() {
        return new JmxRulesEngineBuilder();
    }

    public JmxRulesEngineBuilder() {
        skipOnFirstAppliedRule = false;
        rulePriorityThreshold = EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD;
    }

    public JmxRulesEngineBuilder withSkipOnFirstAppliedRule(boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        return this;
    }

    public JmxRulesEngineBuilder withRulePriorityThreshold(int rulePriorityThreshold) {
        this.rulePriorityThreshold = rulePriorityThreshold;
        return this;
    }

    public DefaultJmxRulesEngine build() {
        return new DefaultJmxRulesEngine(skipOnFirstAppliedRule, rulePriorityThreshold);
    }

}

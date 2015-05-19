package org.easyrules.core;

import org.easyrules.util.EasyRulesConstants;

public class RulesEngineBuilder {

    private boolean skipOnFirstAppliedRule;

    private int rulePriorityThreshold;

    public static RulesEngineBuilder aNewRulesEngine() {
        return new RulesEngineBuilder();
    }

    public RulesEngineBuilder() {
        skipOnFirstAppliedRule = false;
        rulePriorityThreshold = EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD;
    }

    public RulesEngineBuilder withSkipOnFirstAppliedRule(boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        return this;
    }

    public RulesEngineBuilder withRulePriorityThreshold(int rulePriorityThreshold) {
        this.rulePriorityThreshold = rulePriorityThreshold;
        return this;
    }

    public DefaultRulesEngine build() {
        return new DefaultRulesEngine(skipOnFirstAppliedRule, rulePriorityThreshold);
    }

}

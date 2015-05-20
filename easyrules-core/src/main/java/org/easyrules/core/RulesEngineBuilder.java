package org.easyrules.core;

import org.easyrules.api.RuleListener;
import org.easyrules.util.EasyRulesConstants;

import java.util.ArrayList;
import java.util.List;

public class RulesEngineBuilder {

    private boolean skipOnFirstAppliedRule;

    private boolean skipOnFirstFailedRule;

    private int rulePriorityThreshold;

    private List<RuleListener> ruleListeners;

    public static RulesEngineBuilder aNewRulesEngine() {
        return new RulesEngineBuilder();
    }

    public RulesEngineBuilder() {
        skipOnFirstAppliedRule = false;
        skipOnFirstFailedRule = false;
        ruleListeners = new ArrayList<RuleListener>();
        rulePriorityThreshold = EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD;
    }

    public RulesEngineBuilder withSkipOnFirstAppliedRule(boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        return this;
    }

    public RulesEngineBuilder withSkipOnFirstFailedRule(boolean skipOnFirstFailedRule) {
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
        return this;
    }

    public RulesEngineBuilder withRulePriorityThreshold(int rulePriorityThreshold) {
        this.rulePriorityThreshold = rulePriorityThreshold;
        return this;
    }

    public RulesEngineBuilder withRuleListener(RuleListener ruleListener) {
        this.ruleListeners.add(ruleListener);
        return this;
    }

    public DefaultRulesEngine build() {
        return new DefaultRulesEngine(skipOnFirstAppliedRule, skipOnFirstFailedRule, rulePriorityThreshold, ruleListeners);
    }

}

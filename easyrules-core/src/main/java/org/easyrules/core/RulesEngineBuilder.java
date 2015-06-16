package org.easyrules.core;

import org.easyrules.api.RuleListener;
import org.easyrules.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for rules engine instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class RulesEngineBuilder {

    private String name;

    private boolean skipOnFirstAppliedRule;

    private boolean skipOnFirstFailedRule;

    private boolean silentMode;

    private int rulePriorityThreshold;

    private List<RuleListener> ruleListeners;

    public static RulesEngineBuilder aNewRulesEngine() {
        return new RulesEngineBuilder();
    }

    private RulesEngineBuilder() {
        skipOnFirstAppliedRule = false;
        skipOnFirstFailedRule = false;
        ruleListeners = new ArrayList<RuleListener>();
        rulePriorityThreshold = Utils.DEFAULT_RULE_PRIORITY_THRESHOLD;
        name = Utils.DEFAULT_ENGINE_NAME;
    }

    public RulesEngineBuilder named(String name) {
        this.name = name;
        return this;
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

    public RulesEngineBuilder withSilentMode(boolean silentMode) {
        this.silentMode = silentMode;
        return this;
    }

    public DefaultRulesEngine build() {
        return new DefaultRulesEngine(name, skipOnFirstAppliedRule, skipOnFirstFailedRule, rulePriorityThreshold,
                ruleListeners, silentMode);
    }

}

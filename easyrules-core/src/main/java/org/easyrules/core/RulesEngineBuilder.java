package org.easyrules.core;

import org.easyrules.api.RuleListener;
import org.easyrules.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for rules engine instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

    public RulesEngineBuilder named(final String name) {
        this.name = name;
        return this;
    }

    public RulesEngineBuilder withSkipOnFirstAppliedRule(final boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        return this;
    }

    public RulesEngineBuilder withSkipOnFirstFailedRule(final boolean skipOnFirstFailedRule) {
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
        return this;
    }

    public RulesEngineBuilder withRulePriorityThreshold(final int rulePriorityThreshold) {
        this.rulePriorityThreshold = rulePriorityThreshold;
        return this;
    }

    public RulesEngineBuilder withRuleListener(final RuleListener ruleListener) {
        this.ruleListeners.add(ruleListener);
        return this;
    }

    public RulesEngineBuilder withSilentMode(final boolean silentMode) {
        this.silentMode = silentMode;
        return this;
    }

    public DefaultRulesEngine build() {
        return new DefaultRulesEngine(name, skipOnFirstAppliedRule, skipOnFirstFailedRule, rulePriorityThreshold,
                ruleListeners, silentMode);
    }

}

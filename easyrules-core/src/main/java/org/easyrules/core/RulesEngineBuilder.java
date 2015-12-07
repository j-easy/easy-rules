package org.easyrules.core;

import org.easyrules.api.RuleListener;
import org.easyrules.api.RulesEngine;
import org.easyrules.util.Utils;

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
        parameters = new RulesEngineParameters(Utils.DEFAULT_ENGINE_NAME, false, false, Utils.DEFAULT_RULE_PRIORITY_THRESHOLD, false);
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

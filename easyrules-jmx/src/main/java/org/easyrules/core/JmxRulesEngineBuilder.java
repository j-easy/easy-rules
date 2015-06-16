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

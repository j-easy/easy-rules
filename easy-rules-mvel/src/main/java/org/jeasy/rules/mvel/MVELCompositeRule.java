package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.core.RuleProxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public abstract class MVELCompositeRule extends MVELRule {

    protected Set<Rule> rules;

    private Map<Object, Rule> proxyRules;

    /**
     * Create a new MVEL composite rule.
     */
    public MVELCompositeRule() {
        super();
        rules = new TreeSet<>();
        proxyRules = new HashMap<>();
    }

    /**
     * Add a rule to the composite rule.
     * @param rule the rule to add
     */
    public void addRule(final Object rule) {
        Rule proxy = RuleProxy.asRule(rule);
        rules.add(proxy);
        proxyRules.put(rule, proxy);
    }

    /**
     * Remove a rule from the composite rule.
     * @param rule the rule to remove
     */
    public void removeRule(final Object rule) {
        Rule proxy = proxyRules.get(rule);
        if (proxy != null) {
            rules.remove(proxy);
        }
    }

}

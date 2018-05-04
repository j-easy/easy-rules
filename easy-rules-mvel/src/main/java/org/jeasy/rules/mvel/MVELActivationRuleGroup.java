package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

public class MVELActivationRuleGroup extends MVELCompositeRule {

    private Rule selectedRule;

    public MVELActivationRuleGroup() { super(); }

    /**
     * Set rule name.
     *
     * @param name of the rule
     * @return this rule
     */
    public MVELActivationRuleGroup name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return this rule
     */
    public MVELActivationRuleGroup description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return this rule
     */
    public MVELActivationRuleGroup priority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public boolean evaluate(Facts facts) {
        for (Rule rule : rules) {
            if (rule.evaluate(facts)) {
                selectedRule = rule;
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        selectedRule.execute(facts);
    }
}

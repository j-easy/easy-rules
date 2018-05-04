package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

public class MVELUnitRuleGroup extends MVELCompositeRule {

    public MVELUnitRuleGroup() {
        super();
    }

    /**
     * Set rule name.
     *
     * @param name of the rule
     * @return this rule
     */
    public MVELUnitRuleGroup name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return this rule
     */
    public MVELUnitRuleGroup description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return this rule
     */
    public MVELUnitRuleGroup priority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public boolean evaluate(Facts facts) {
        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if (!rule.evaluate(facts)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        for (Rule rule : rules) {
            rule.execute(facts);
        }
    }
}

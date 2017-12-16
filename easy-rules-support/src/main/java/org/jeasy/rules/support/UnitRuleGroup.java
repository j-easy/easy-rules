package org.jeasy.rules.support;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

/**
 * A unit rule group is a composite rule that acts as a unit: Either all rules are applied or nothing is applied.
 *
 *  @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class UnitRuleGroup extends CompositeRule {

    /**
     * Create a unit rule group.
     */
    public UnitRuleGroup() {
    }

    /**
     * Create a unit rule group.
     * @param name of the composite rule
     */
    public UnitRuleGroup(String name) {
        super(name);
    }

    /**
     * Create a unit rule group.
     * @param name of the composite rule
     * @param description of the composite rule
     */
    public UnitRuleGroup(String name, String description) {
        super(name, description);
    }

    /**
     * Create a unit rule group.
     * @param name of the composite rule
     * @param description of the composite rule
     * @param priority of the composite rule
     */
    public UnitRuleGroup(String name, String description, int priority) {
        super(name, description, priority);
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

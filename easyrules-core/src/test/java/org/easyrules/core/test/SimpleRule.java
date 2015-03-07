package org.easyrules.core.test;

import org.easyrules.core.BasicRule;

/**
 * Simple rule class used for tests.
 * 
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class SimpleRule extends BasicRule {

    /**
     * Has the rule been executed? .
     */
    protected boolean executed;

    public SimpleRule(String name, String description) {
        super(name, description);
    }

    public SimpleRule(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public boolean evaluateConditions() {
        return true;
    }

    @Override
    public void performActions() throws Exception {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}

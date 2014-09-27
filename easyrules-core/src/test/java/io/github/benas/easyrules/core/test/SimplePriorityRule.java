package io.github.benas.easyrules.core.test;

import io.github.benas.easyrules.core.BasicPriorityRule;

/**
 * Simple priority rule class used for tests.
 * 
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class SimplePriorityRule extends BasicPriorityRule {

    /**
     * Has the rule been executed? .
     */
    protected boolean executed;

    public SimplePriorityRule(String name, String description, int priority) {
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

package io.github.benas.easyrules.core.test;

import io.github.benas.easyrules.core.CompositePriorityRule;

/**
 * Simple composite rule class used for tests.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class SimpleCompositePriorityRule extends CompositePriorityRule {

    protected boolean executed;

    public SimpleCompositePriorityRule(String name, String description) {
        super(name, description);
    }

    @Override
    public void performActions() throws Exception {
        super.performActions();
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}

package io.github.io.github.benas.easyrules.core;

import io.github.benas.easyrules.core.CompositeRule;

/**
 * Simple composite rule class used for tests.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class SimpleCompositeRule extends CompositeRule {

    protected boolean executed;

    public SimpleCompositeRule(String name, String description, int priority) {
        super(name, description, priority);
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

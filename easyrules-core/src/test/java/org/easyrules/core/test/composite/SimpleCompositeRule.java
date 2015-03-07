package org.easyrules.core.test.composite;

import org.easyrules.core.CompositeRule;

/**
 * Simple composite rule class used for tests.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class SimpleCompositeRule extends CompositeRule {

    protected boolean executed;

    public SimpleCompositeRule(String name, String description) {
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

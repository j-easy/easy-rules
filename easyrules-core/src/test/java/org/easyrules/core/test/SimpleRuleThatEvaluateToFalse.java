package org.easyrules.core.test;

/**
 * Simple priority rule class used for tests.
 * 
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class SimpleRuleThatEvaluateToFalse extends SimpleRule {

    public SimpleRuleThatEvaluateToFalse(String name, String description) {
        super(name, description);
    }

    public SimpleRuleThatEvaluateToFalse(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public boolean evaluateConditions() {
        return false;
    }

}

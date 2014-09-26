package io.github.io.github.benas.easyrules.core;

/**
 * Simple rule class used for tests.
 * 
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class SimpleRuleThatEvaluateToFalse extends SimpleRule {

    public SimpleRuleThatEvaluateToFalse(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public boolean evaluateConditions() {
        return false;
    }

}

package io.github.benas.easyrules.core.test;

/**
 * Simple priority rule class used for tests.
 * 
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class SimplePriorityRuleThatEvaluateToFalse extends SimplePriorityRule {

    public SimplePriorityRuleThatEvaluateToFalse(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public boolean evaluateConditions() {
        return false;
    }

}

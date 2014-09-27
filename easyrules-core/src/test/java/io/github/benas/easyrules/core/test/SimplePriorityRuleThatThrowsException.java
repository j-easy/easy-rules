package io.github.benas.easyrules.core.test;

/**
 * Simple priority rule class used for tests.
 * 
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class SimplePriorityRuleThatThrowsException extends SimplePriorityRule {

    public SimplePriorityRuleThatThrowsException(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public void performActions() throws Exception {
        throw new Exception("An exception occurred in SimplePriorityRuleThatThrowsException.performActions");
    }

}

package io.github.io.github.benas.easyrules.core;

/**
 * Simple rule class used for tests.
 * 
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class SimpleRuleThatThrowsException extends SimpleRule {

    public SimpleRuleThatThrowsException(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public void performActions() throws Exception {
        throw new Exception("An exception occurred in SimpleRuleThatThrowsException.performActions");
    }

}

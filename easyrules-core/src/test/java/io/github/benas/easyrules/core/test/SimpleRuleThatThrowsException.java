package io.github.benas.easyrules.core.test;

/**
 * Simple rule class used for tests.
 * 
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class SimpleRuleThatThrowsException extends SimpleRule {

    public SimpleRuleThatThrowsException(String name, String description) {
        super(name, description);
    }

    @Override
    public void performActions() throws Exception {
        throw new Exception("An exception occurred in SimpleRuleThatThrowsException.performActions");
    }

}

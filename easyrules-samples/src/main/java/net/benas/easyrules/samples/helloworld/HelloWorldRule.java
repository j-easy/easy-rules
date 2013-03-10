package net.benas.easyrules.samples.helloworld;

import net.benas.easyrules.core.Rule;

/**
 * Hello World rule class.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class HelloWorldRule extends Rule {

    /**
     * The user input
     */
    private String input;

    public HelloWorldRule(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public boolean evaluateConditions() {
        //The rule should be applied only if the user's response is yes (duke friend)
        return input.equalsIgnoreCase("yes");
    }

    @Override
    public void performActions() throws Exception {
        //When rule conditions are satisfied, prints 'Hello duke's friend!' to the console
        System.out.println("Hello duke's friend!");
    }

    public void setInput(String input) {
        this.input = input;
    }
}

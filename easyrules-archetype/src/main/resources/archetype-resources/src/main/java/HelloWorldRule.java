package ${packageName};

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "Hello World rule", description = "Say Hello to duke's friends only")
public class HelloWorldRule {

    /**
     * The user input which represents the data that the rule will operate on.
     */
    private String input;

    @Condition
    public boolean checkInput() {
        //The rule should be applied only if the user's response is yes (duke friend)
        return input.equalsIgnoreCase("yes");
    }

    @Action
    public void sayHelloToDukeFriend() throws Exception {
        //When rule conditions are satisfied, prints 'Hello duke's friend!' to the console
        System.out.println("Hello duke's friend!");
    }

    public void setInput(String input) {
        this.input = input;
    }

}

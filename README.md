## What is Easy Rules?

Easy Rules is a simple yet powerful Java Rules Engine.

It provides useful abstractions to define business rules and apply them easily.

## Documentation

### Quick introduction

Most business rules can be represented by the following rule definition :

 * Name : Unique rule name

 * Description : A brief description of the rule

 * Priority : Rule priority regarding to other rules

 * Conditions : Set of conditions that should be satisfied to apply the rule

 * Actions : Set of actions to perform when conditions are satisfied

Easy Rules simply provides an abstraction for each of these key points that define a business rule.

### Easy Rules Key API

A rule in Easy Rules is an instance of the `Rule` class :

```java
public class Rule implements Comparable<Rule> {

    private String name;

    private String description;

    private int priority;

    public boolean evaluateConditions() {return false;}

    public void performActions() throws Exception {}

    //getters, setters and compareTo methods omitted

}
```

The `name`, `description` and `priority` attributes are self explanatory.

The `evaluateConditions` method encapsulates conditions that must evaluate to TRUE to trigger the rule.

The `performActions` method encapsulates actions that should be performed when rule's conditions are satisfied.

Of course, evaluating conditions and performing actions should be delegated to other objects if used across multiple rules.

### Easy Rules engine

Easy Rules engine handles a registry of rules with unique names. These rules are applied according to their priorities.
By default, lower values represent higher priorities. To override this default behavior, you can extend the `Rule` class
and override `compareTo` method to provide a custom priority strategy.

Easy Rules provide the following parameters:

| Parameter              | Type     | Required | Default  | Description                                                      |
|:----------------------:|:--------:|:--------:|:--------:|------------------------------------------------------------------|
| skipOnFirstAppliedRule | boolean  | no       | false    | skip next applicable rules when a rule is applied                |
| rulePriorityThreshold  | int      | no       | 10000    | skip next rules if priority exceeds a user defined threshold.    |

## Hello World Sample

This sample shows how to use Easy Rules to say Hello to only duke's friends.
The program asks the user if he/she is a friend of duke and says Hello only if he/she responds yes!

The rule class is the following :

```java
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
```

The launcher class is the following :

```java
public class HelloWorldSampleLauncher {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you a friend of duke? [yes/no]:");
        String input = scanner.nextLine();

        /**
         * Define the rule
         */
        HelloWorldRule helloWorldRule = new HelloWorldRule("Hello World rule", "Say Hello to only duke's friends", 1);

        /**
         * Set data to operate on
         */
        helloWorldRule.setInput(input.trim());

        /**
         * Create a default rules engine and register the business rule
         */
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerRule(helloWorldRule);

        /**
         * Fire rules
         */
        rulesEngine.fireRules();

    }
}
```

More samples of how to use Easy Rules can be found [here][].

## License
Easy Rules is released under the [MIT License][].

[here]: https://github.com/benas/easy-rules/tree/master/easyrules-samples
[MIT License]: http://opensource.org/licenses/mit-license.php/
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

### Easy Rules Key APIs

A rule in Easy Rules is an instance of the `Rule` class :

```java
public class Rule implements Comparable<Rule> {

    private String name;

    private String description;

    private int priority;

    private ConditionTrigger conditionTrigger;

    private ActionPerformer actionPerformer;

    //getters, setters and compareTo methods omitted

}
```

The `name`, `description` and `priority` attributes are self explanatory.

The `ConditionTrigger` interface is an abstraction of all conditions that trigger the rule :

```java
public interface ConditionTrigger {

    /**
     * The condition that triggers the rule.
     * @return true if the rule should be applied, false else
     */
    boolean triggerCondition();

}
```

Implementations of this interface should encapsulate conditions logic that should be satisfied to apply the rule's actions.

The `ActionPerformer` interface is an abstraction of all actions that should be performed when rule's conditions are satisfied :

```java
public interface ActionPerformer {

    /**
     * Perform an action when a rule is triggered.
     * @throws Exception if an exception occurs during action performing
     */
    void performAction() throws Exception;

}
```

Easy Rules provides a rule builder to create rules easily like the following snippet :

```java
Rule rule = new RuleBuilder()
        .name("Hello World Rule")
        .description("Say Hello to only duke's friends")
        .conditionTrigger(new HelloWorldConditionTrigger(input.trim()))
        .actionPerformer(new HelloWorldActionPerformer())
        .build();
```


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

```java
public class HelloWorldSampleLauncher {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you a friend of duke? [yes/no]:");
        String input = scanner.nextLine();

        /**
         * Define the rule
         */
        Rule rule = new RuleBuilder()
                .name("Hello World Rule")
                .description("Say Hello to only duke's friends")
                .conditionTrigger(new HelloWorldConditionTrigger(input.trim()))
                .actionPerformer(new HelloWorldActionPerformer())
                .build();

        /**
         * Create a default rules engine and register the business rule
         */
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerRule(rule);

        /**
         * Fire rules
         */
        rulesEngine.fireRules();

    }
}
```

`HelloWorldConditionTrigger` encapsulates the logic that checks if the user responded 'yes' to the question:

```java
public class HelloWorldConditionTrigger implements ConditionTrigger {

    private String input;

    public HelloWorldConditionTrigger(String input) {
        this.input = input;
    }

    public boolean triggerCondition() {
        //The rule should be applied only if the user's response is yes (duke friend)
        return input.equalsIgnoreCase("yes");
    }

}
```

`HelloWorldActionPerformer` encapsulates the logic of the action to perform when the condition is satisfied, in this sample, simply prints 'Hello duke's friend!' to the console:

```java
public class HelloWorldActionPerformer implements ActionPerformer {

    @Override
    public void performAction()  {
        System.out.println("Hello duke's friend!");
    }

}
```

More samples of how to use Easy Rules can be found [here][].


## Roadmap

 * Annotation support : Condition and Action can be defined in any POJO

 * Spring support : Easy Rules should be easily configured and used in a Spring container

## License
Easy Rules is released under the [MIT License][].

## Contribution
Your feedback is highly appreciated! For any issue, please use the [issue tracker][].

You can also contribute with pull requests on github.

Many thanks upfront!

[here]: https://github.com/benas/easy-rules/tree/master/easyrules-samples
[MIT License]: http://opensource.org/licenses/mit-license.php/
[issue tracker]: https://github.com/benas/easy-rules/issues
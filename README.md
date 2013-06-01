## What is Easy Rules?

Easy Rules is a simple yet powerful Java Rules Engine providing the following features :

 * Lightweight framework and easy to learn API

 * Useful abstractions to define business rules and apply them easily using Java

 * The ability to create composite rules from primitive ones

 * Dynamic rule reconfiguration at runtime using JMX

## Presentation

You can find some slides about Easy Rules at [speaker deck][].

## Documentation

### Getting started

Easy Rules core is a single jar file with no dependencies. To build Easy Rules from sources, you need to have maven installed and set up.

To use Easy Rules, please follow these instructions :

 * $>`git clone https://github.com/benas/easy-rules.git`

 * $>`mvn package`

 * Add the generated jar `target/easyrules-core-${version}.jar` to your application's classpath

### Quick introduction

Most business rules can be represented by the following rule definition :

 * Name : A unique rule name within a rules namespace

 * Description : A brief description of the rule

 * Priority : Rule priority regarding to other rules

 * Conditions : Set of conditions that should be satisfied to apply the rule

 * Actions : Set of actions to perform when conditions are satisfied

Easy Rules provides an abstraction for each of these key points that define a business rule.

### Easy Rules Key API

A rule in Easy Rules is an implementation of the `Rule` interface :

```java
public interface Rule {

    /**
     * Rule conditions abstraction : this method encapsulates the rule's conditions.
     * @return true if the rule should be applied, false else
     */
    boolean evaluateConditions();

    /**
     * Rule actions abstraction : this method encapsulates the rule's actions.
     * @throws Exception thrown if an exception occurs during actions performing
     */
    void performActions() throws Exception;

    //Getters and setters for rule name, description and priority omitted.

}
```

The `evaluateConditions` method encapsulates conditions that must evaluate to TRUE to trigger the rule.

The `performActions` method encapsulates actions that should be performed when rule's conditions are satisfied.

Easy Rules provides a simple implementation of the `Rule` interface named `BasicRule`. This class implements most of methods

defined in the `Rule` interface. To define a rule, you can extends this class and override `evaluateConditions` and

`performActions` methods to provide your conditions and actions logic.

Evaluating conditions and performing actions should be delegated to other objects if used across multiple rules.

### Easy Rules engine

Easy Rules engine handles a registry of rules with unique names. Each instance of Easy Rules engine can be seen as a separate namespace.
Rules are applied according to their priorities. By default, lower values represent higher priorities. To override this default behavior, you can extend the `BasicRule` class
and override `compareTo` method to provide a custom priority strategy.

Easy Rules provide the following parameters:

| Parameter              | Type     | Required | Default  | Description                                                      |
|:----------------------:|:--------:|:--------:|:--------:|------------------------------------------------------------------|
| skipOnFirstAppliedRule | boolean  | no       | false    | skip next applicable rules when a rule is applied                |
| rulePriorityThreshold  | int      | no       | 10000    | skip next rules if priority exceeds a user defined threshold.    |

### Hello World Sample

This sample shows how to use Easy Rules to say Hello to only duke's friends.
The program asks the user if he is a friend of duke and says Hello only if he responds yes!

The rule class is the following :

```java
public class HelloWorldRule extends BasicRule {

    /**
     * The user input.
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

### Composite Rules

Easy Rules allows you to create complex rules from primitive ones. A `CompositeRule` is composed of a set of rules.

This is typically an implementation of the composite design pattern.

A composite rule is triggered if all conditions of its composing rules are satisfied.

When a composite rule is applied, actions of all composing rules are performed in the natural order of

rules which is rules priorities by default.

### JMX Managed Rules

This is the most interesting feature of Easy Rules. Being able to dynamically reconfigure business rules at runtime

in production systems is a recurrent requirement. Thanks to JMX, Easy Rules can expose rules attributes to be

managed via any JMX compliant client.

The key API to use this feature is the `JmxManagedRule` interface which extends the `Rule` interface to add JMX

monitoring and management capabilities.

```java
@javax.management.MXBean
public interface JmxManagedRule extends Rule {

}
```

Easy Rules provides a simple implementation class of this interface called `BasicJmxManagedRule`.

To make your rule manageable via JMX, you can extend this class and register it in Easy Rules engine as a `JmxManagedRule`:

```java
RulesEngine rulesEngine = new DefaultRulesEngine();
rulesEngine.registerJmxManagedRule(myJmxManagedRule);
```

By default, rule description and priority are exposed as JMX manageable attributes. If you need to expose more specific

attributes, you can extend the `JmxManagedRule` interface and add getters and setters of your manageable attributes.

An example of using dynamic rule reconfiguration at runtime is provided in the [order tutorial][].

## License
Easy Rules is released under the [MIT License][].

[speaker deck]: https://speakerdeck.com/benas/easy-rules
[here]: https://github.com/benas/easy-rules/tree/master/easyrules-samples
[order tutorial]: https://github.com/benas/easy-rules/tree/master/easyrules-samples/src/main/java/net/benas/easyrules/samples/order
[MIT License]: http://opensource.org/licenses/mit-license.php/

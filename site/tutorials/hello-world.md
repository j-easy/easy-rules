---
layout: docs
title: Hello World
header: Hello World tutorial
prev_section: user-guide/managing-rules
next_section: tutorials/dynamic-configuration
doc: true
---

This tutorial shows how to use Easy Rules on a very simple application.
 The program should ask the user if he is a friend of duke and says 'Hello duke's friend!' only if he replies 'yes'.

Based on this requirement, the rule is pretty straightforward :

* The condition is that the user input must be equal to 'yes'
* The action is to say 'Hello duke's friend!' to the user

First, let's create a rule class:

```java
@Rule(name = "Hello World rule",
    description = "Say Hello to only duke's friends")
public class HelloWorldRule {

    /**
     * The user input which represents the data
     * that the rule will operate on.
     */
    private String input;

    @Condition
    public boolean checkInput() {
        //The rule should be applied only if
        //the user's response is yes (duke friend)
        return input.equalsIgnoreCase("yes");
    }

    @Action
    public void sayHelloToDukeFriend() throws Exception {
        //When rule conditions are satisfied,
        //prints 'Hello duke's friend!' to the console
        System.out.println("Hello duke's friend!");
    }

    public void setInput(String input) {
        this.input = input;
    }

}
```

Then, we have to register an instance of this rule to an Easy Rules engine and launch the program with the following class :

```java
public class HelloWorldSampleLauncher {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you a friend of duke?[yes/no]:");
        String input = scanner.nextLine();

        /**
         * Declare the rule
         */
        HelloWorldRule helloWorldRule = new HelloWorldRule();

        /**
         * Set business data to operate on
         */
        helloWorldRule.setInput(input.trim());

        /**
         * Create a rules engine and register the business rule
         */
        AnnotatedRulesEngine rulesEngine =
                                new AnnotatedRulesEngine();
        rulesEngine.registerRule(helloWorldRule);

        /**
         * Fire rules
         */
        rulesEngine.fireRules();

    }
}
```

To run this tutorial, you can follow these instructions from the root directory of Easy Rules :

{% highlight bash %}
$ mvn install
$ cd easyrules-samples
$ mvn exec:java -P runHelloWorldTutorial
{% endhighlight %}

If you run this tutorial, you would get the following output:

```
Are you a friend of duke? [yes/no]:
yes
10 sep. 2014 13:26:19 org.easyrules.core.DefaultRulesEngine fireRules
INFO: Rule 'Hello World rule' triggered.
Hello duke's friend!
10 sep. 2014 13:26:19 org.easyrules.core.DefaultRulesEngine fireRules
INFO: Rule 'Hello World rule' performed successfully.
```


---
layout: docs
title: Rule Priority
header: Rule Priority tutorial
prev_section: tutorials/hello-world
next_section: tutorials/dynamic-configuration
doc: true
---

This tutorial shows how define priority in which rules must be fired.
In Easy Rules, every rule has a priority. Rules are fired by default according to their priorities (this can be changed as described in the [user guide]({{site.url}}/user-guide/defining-rules.html#rules-priorities)).

In this tutorial, we have an application that sells alcohol. The application must flag the customer as adult if his age is greater than 18,
and must deny children from buying alcohol. Customers are represented by the following `Person` class:

```java
public class Person {

    private String name;

    private int age;

    private boolean adult;

    //getters and setters omitted

}
```

Based on these requirements, we can define the following rules:

* Rule 1: This rule should operate an a `Person` instance, check that the peron age is greater than 18 and set the adult flag.
* Rule 2: This rule should operate an a `Person` instance, check that the peron is adult and deny children (ie, non adult) from buying alcohol.

Rule 1 should be fired **_before_** rule 2. We will set rule 1 priority to 1 and rule 2 priority to 2 so that Easy Rules engine fire them in this order.

First, let's create a class for rule 1:

```java
@Rule(name = "AgeRule",
      description = "Check if person's age is > 18
                     and marks the person as adult")
public class AgeRule {

    private Person person;

    private int adultAge = 18;

    public AgeRule(Person person) {
        this.person = person;
    }

    @Condition
    public boolean isAdult() {
        return person.getAge() > adultAge;
    }

    @Action
    public void markAsAdult(){
        person.setAdult(true);
        System.out.printf(
            "Person %s has been marked as adult.\n",
             person.getName());
    }

    @Priority
    public int getPriority() {
        return 1;
    }

}
```
As required, this rule class operates on a person that is passed at construction time.

The `isAdult` method annotated with `@Condition` tells Easy Rules engine to call this method to check if the rule should be fired, in this case, if the person's age is greater than 18.

The `markAsAdult` method annotated with `@Action` will mark the person as adult by setting the `adult` flag.

Finally, the `getPriority` method annotated with `@Priority` tells Easy Rules engine to fire this rule in first order.

Now, let's a class for rule 2:

```java
@Rule(name = "alcoholRule",
      description = "Children are not allowed to buy alcohol.")
public class AlcoholRule {

    private Person person;

    public AlcoholRule(Person person) {
        this.person = person;
    }

    @Condition
    public boolean isChildren() {
        return !person.isAdult();
    }

    @Action
    public void denyAlcohol(){
        System.out.printf(
            "Sorry %s, you are not allowed to buy alcohol.\n",
             person.getName());
    }

    @Priority
    public int getPriority() {
        return 2;
    }

}
```
As for rule 1, the class operates on a person instance and provides methods to define rule condition, action and priority.

To launch the tutorial, we will use the following class:

```java
public class Launcher {

    public static void main(String[] args) {

        //create a person instance
        Person tom = new Person("Tom", 16);
        System.out.println(
            "Tom: Hi! can I have some Vodka please?");

        //create a rules engine
        AnnotatedRulesEngine annotatedRulesEngine =
                                new AnnotatedRulesEngine();

        //register rules
        annotatedRulesEngine.registerRule(new AgeRule(tom));
        annotatedRulesEngine.registerRule(new AlcoholRule(tom));

        //fire rules
        annotatedRulesEngine.fireRules();

    }

}
```

To run the tutorial, you can follow these instructions from the root directory of Easy Rules :

{% highlight bash %}
$ mvn install
$ cd easyrules-samples
$ mvn exec:java -P runRulePriorityTutorial
{% endhighlight %}

If you run this tutorial, you would get the following output:

```
Tom: Hi! can I have some Vodka please?
Oct 13, 2014 9:37:14 PM org.easyrules.core.AnnotatedRulesEngine fireRules
INFO: Rule alcoholRule triggered.
Sorry Tom, you are not allowed to buy alcohol.
Oct 13, 2014 9:37:14 PM org.easyrules.core.AnnotatedRulesEngine fireRules
INFO: Rule alcoholRule performed successfully.
```

As you can see, since Tom's age is under 18, he has not been allowed to buy alcohol as expected.


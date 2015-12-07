---
layout: docs
title: Shop tutorial
header: Shop tutorial
prev_section: tutorials/hello-world-tutorial
next_section: tutorials/scheduler-tutorial
doc: true
---

In this tutorial, we have a shop application and we would like to implement the following requirement: deny children from buying alcohol.
The minimal legal age to be considered as adult is 18. This tutorial is split in two parts:

1. Part 1: Implement the business rule of denying children from buying alcohol
2. Part 2: Make the legal age configurable via JMX so we can change it at runtime

## Part 1 : Implement the business rule

Our shop customers are represented by the _Person_ class:

```java
public class Person {
    private String name;
    private int age;
    private boolean adult;
    //getters and setters omitted
}
```

We will define the following rules:

* Rule 1: should operate an a `Person` instance, check that the person age is greater than 18 and set the adult flag.
* Rule 2: should operate an a `Person` instance, check that the person is adult and deny children (ie, non adult) from buying alcohol.

Rule 1 should be fired **_before_** rule 2. We will set rule 1 priority to 1 and rule 2 priority to 2 so that Easy Rules engine fire them in this order.

First, let's create a class for rule 1:

```java
public class AgeRule extends BasicRule {

    private static final int ADULT_AGE = 18;

    private Person person;

    public AgeRule(Person person) {
        super("AgeRule",
              "Check if person's age is > 18 and
               marks the person as adult", 1);
        this.person = person;
    }

    @Override
    public boolean evaluate() {
        return person.getAge() > ADULT_AGE;
    }

    @Override
    public void execute() {
        person.setAdult(true);
        System.out.printf("Person %s has been marked as adult",
                            person.getName());
    }
    
}
```

As required, this rule class operates on a person that is passed at construction time.

The `evaluate` method checks if the person's age is greater than 18.

The `execute` will mark the person as adult by setting the `adult` flag.

Finally, the third constructor argument which represents the rule priority is set to 1 to tells Easy Rules engine to fire this rule in first order.

Now, let's create a class for rule 2:

```java
public class AlcoholRule extends BasicRule {

    private Person person;

    public AlcoholRule(Person person) {
        super("AlcoholRule", 
              "Children are not allowed to buy alcohol",
               2);
        this.person = person;
    }

    @Condition
    public boolean evaluate() {
        return !person.isAdult();
    }

    @Action
    public void execute(){
        System.out.printf("Shop: Sorry %s,
                you are not allowed to buy alcohol",
                 person.getName());
    }

}
```

As for rule 1, the class operates on a person instance and prints the denial message for children.

To launch the tutorial, we will use the following class:

```java
public class Launcher {

    public static void main(String[] args) {
        //create a person instance
        Person tom = new Person("Tom", 14);
        System.out.println("Tom:
                Hi! can I have some Vodka please?");

        //create a rules engine
        RulesEngine rulesEngine = aNewRulesEngine()
                .named("shop rules engine")
                .build();

        //register rules
        rulesEngine.registerRule(new AgeRule(tom));
        rulesEngine.registerRule(new AlcoholRule(tom));

        //fire rules
        rulesEngine.fireRules();
    }

}
```

To run the first part of the tutorial, you can follow these instructions from the root directory of Easy Rules :

{% highlight bash %}
$ mvn install
$ cd easyrules-samples
$ mvn exec:java -P runShopTutorialPart1
{% endhighlight %}

You should get the following output:

```
Tom: Hi! can I have some Vodka please?
INFO: Rule alcoholRule triggered.
Shop: Sorry Tom, you are not allowed to buy alcohol
INFO: Rule alcoholRule performed successfully.
```

As expected, since Tom's age is under 18, he has not been allowed to buy alcohol.

## Part 2 : Changing the legal adult age at runtime

In this second part, we will expose the legal adult age as a JMX attribute. 
So first, let's define an interface that allows us to change this age via JMX:

```java
@javax.management.MXBean
public interface AgeJmxRule extends JmxRule {
    
    int getAdultAge();
    
    void setAdultAge(int adultAge);

}
```

Then, we should make our `AgeRule` implement the `AgeJmxRule` interface to expose the the legal adult age as a JMX attribute.
So here is the new `AgeRule` class:

```java
public class AgeRule extends BasicRule implements AgeJmxRule {

    private int adultAge = 18;

    private Person person;

    public AgeRule(Person person) {
        super("AgeRule", 
              "Check if person's age is > 18 
               and marks the person as adult", 1);
        this.person = person;
    }

    @Override
    public boolean evaluate() {
        return person.getAge() > adultAge;
    }

    @Override
    public void execute() {
        person.setAdult(true);
        System.out.printf("Person %s has been marked as adult",
                person.getName());
    }
    
    @Override
    public int getAdultAge() {
        return adultAge;
    }

    @Override
    public void setAdultAge(int adultAge) {
        this.adultAge = adultAge;
    }
}
```

Finally, let's suspend the program to change the legal adult age value at runtime via any compliant JMX client and see the engine behavior after this change:

```java
public class Launcher {

    public static void main(String[] args) {

        //create a person instance
        Person tom = new Person("Tom", 14);
        System.out.println("Tom: 
                    Hi! can I have some Vodka please?");

        //create a Jmx rules engine
        JmxRulesEngine rulesEngine = aNewJmxRulesEngine()
                .named("shop rules engine")
                .build();

        //register rules
        rulesEngine.registerJmxRule(new AgeRule(tom));
        rulesEngine.registerRule(new AlcoholRule(tom));

        //fire rules
        rulesEngine.fireRules();

        // Update adult age via a JMX client.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Change adult age via a JMX client 
                            and then press enter");
        scanner.nextLine();

        System.out.println("Re fire rules after
                            updating adult age...");

        rulesEngine.fireRules();
    }

}
```

To run the second part of the tutorial, run the following command:
{% highlight bash %}
$ mvn exec:java -P runShopTutorialPart2
{% endhighlight %}

You will be asked to change the legal adult age via a JMX compliant client.

In the next screenshot, we use <a href="https://visualvm.java.net" target="_blank">VisualVM</a> to change the age value:

<img style="width: 100%" src="{{site.url}}/img/jmx.png">

If you change the age value to 13 for example, you will see that Tom will be able to buy alcohol since his age (14) is greater than the new legal adult age (13).

That's all! In this tutorial, we have seen how to create a real business rule with Easy Rules and how to reconfigure it at runtime.

---
layout: docs
title: FAQ
prev_section: get-involved/release-notes
doc: true
---

## <a name="1"></a>[1. Why is Easy Rules called the "The stupid Java rules engine"?](#1)

The goal behind Easy Rules is to provide a lightweight rules engine without features that 80% of application 
do not need. The term "stupid" is actually the perfect term to describe how the engine works: It iterates
over a set of ordered rules and execute rules when their conditions are met. This what makes it easy to learn use and 
use following the KISS principle.

## <a name="2"></a>[2. I would like to return a value upon a rule execution, how to do that?](#2)

By design, rules do not return values. But you can always make your rules return a result after execution. Here is an example:

```java
@Rule(name = "my rule")
public static class MyRule<T> {

    private boolean executed;

    private T result;

    //@Condition
    public boolean when() {
        return true;
    }

    //@Action
    public void then() throws MyException {
        try {
            System.out.println("my rule has been executed");
            result = null; // assign your result here
            executed = true;
        } catch (MyException e) {
            // executed flag will remain false if an exception occurs
            throw e;
        }
    }

    public boolean isExecuted() {
        return executed;
    }

    public T getResult() {
        return result;
    }

}
```

This rule will return a result if it executes successfully. After firing rules, you query the `executed` flag on your
rule instance and get the execution result.

## <a name="3"></a>[3. I've registered multiple instances of the same rule with different inputs, but it seems only the first instance is registered. What's happening?](#3)

Rules have unique names within a rules engine registry. If you register multiple instances of the same rule, only the first instance will be considered.
Other instances will be ignored since they have the same name. Let's see an example:

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
            "Person %s has been marked as adult",
             person.getName());
    }

}
```

The `Person` type is a simple POJO having a `name` and `age` fields. Let's register multiple instances of the `AgeRule`:

```java
Person tom = new Person("Tom", 20);
Person david = new Person("David", 19);

RulesEngine rulesEngine = aNewRulesEngine().build();

//first run
AgeRule ageRule = new AgeRule(tom);
rulesEngine.registerRule(ageRule);
rulesEngine.fireRules();

//second run
ageRule = new AgeRule(david);
rulesEngine.registerRule(ageRule);
rulesEngine.fireRules();
```

Both Tom and David are adults, so you are expecting to see:

```
Person Tom has been marked as adult.
Person David has been marked as adult.
```

But actually you get:

```
Person Tom has been marked as adult.
Person Tom has been marked as adult.
```

The second rule instance has been ignored at registration time since it has the same name ( "AgeRule" ) as the first instance.

So how to deal with multiple data using the same rule?

You have 2 solutions: Either you clear rules after the first run using `rulesEngine.clearRules()`, or register your rule only once,
 vary input using a setter (that you should add to your rule) and re-fire rules:

```java
//create persons
Person tom = new Person("Tom", 20);
Person david = new Person("David", 19);

//create a rules engine
RulesEngine rulesEngine = aNewRulesEngine().build();
AgeRule ageRule = new AgeRule();
rulesEngine.registerRule(ageRule);

//first run
ageRule.setPerson(tom);
rulesEngine.fireRules();

//second run
ageRule.setPerson(david);
rulesEngine.fireRules();
```

This is more efficient than registering new instances for each new business data input.

## <a name="4"></a>[4. Is Easy Rules usable with Android?](#4)

Yes. Thanks to the community, Easy Rules has been made Android compatible since version 1.3

## <a name="5"></a>[5. Can I use Easy Rules in a web application?](#5)

Sure. Easy Rules is very lightweight and can be used both in a standalone application or embedded in an application server,
a servlet container or a dependency injection container.

## <a name="6"></a>[6. How to deal with tread safety?](#6)

If you run Easy Rules in a multi-threaded environment, you should take into account the following considerations:

* Easy Rules engine holds a set of rules, it is **not** thread safe.
* By design, rules in Easy Rules encapsulate the business object model they operate on, so they are **not** thread safe neither.

Do not try to make everything synchronized or locked down! Easy Rules engine is very a lightweight object 
and you can create an instance per thread, this is by far the easiest way to avoid thread safety problems.
And if, at this exact point of time reading this line, you are already thinking about performance,
don't forget that <a href="http://c2.com/cgi/wiki?PrematureOptimization" target="_blank"><em>"Premature optimization is the root of all evil" - Donald Knuth</em></a>. 

## <a name="7"></a>[7. I have another question, how do I do?](#7)

Feel free to ask your question in the [Gitter](https://gitter.im/benas/easy-rules) channel of the project: [![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/benas/easy-rules?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

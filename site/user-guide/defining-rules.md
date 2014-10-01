---
layout: docs
title: Defining rules
header: Defining rules
prev_section: user-guide/introduction
next_section: user-guide/rules-engine
doc: true
---

The key API in Easy Rules is the `Rule` interface:

```java
public interface Rule {

    /**
    * This method encapsulates the rule's conditions.
    * @return true if the rule should be applied, false else
    */
    boolean evaluateConditions();

    /**
    * This method encapsulates the rule's actions.
    * @throws Exception thrown if an exception occurs
    * during actions performing
    */
    void performActions() throws Exception;

    //Getters and setters for rule name,
    //description and priority omitted.

}
```

The `evaluateConditions` method encapsulates conditions that must evaluate to _TRUE_ to trigger the rule.

The `performActions` method encapsulates actions that should be performed when rule's conditions are satisfied.

## Defining rules by extending _BasicRule_

Easy Rules provides a simple implementation of the `Rule` interface named `BasicRule`. This class implements most of methods
defined in the `Rule` interface. You can extends this class and override `evaluateConditions` and
`performActions` methods to provide your conditions and actions logic. Here is an example:

```java
public class MyRule extends BasicRule {

    private BusinessData myBusinessData; //data to operate on

    @Override
    public boolean evaluateConditions() {
        //my rule conditions
        return true;
    }

    @Override
    public void performActions() throws Exception {
        //my actions
    }

}
```

## Defining rules using annotations

Easy Rules provides the `@Rule` annotation that can turn a POJO into a rule. Here is an example:

```java
@Rule(name = "my rule", description = "my rule description")
public class MyRule {

    private BusinessData myBusinessData; //data to operate on

    @Condition
    public boolean checkConditions() {
        //my rule conditions
        return true;
    }

    @Action
    public void performActions() throws Exception {
        //my actions
    }

}
```

You can use `@Condition` and `@Action` annotations to mark methods to execute to check rule conditions and perform rule actions
respectively.

<div class="note info">
  <h5>Rules can have multiple actions</h5>
  <p>You can annotate multiple methods with the <em>Action</em> annotation. You can also define the execution order of actions with the
  <em>order</em> attribute: <em>@Action(order = 1)</em>.</p>
</div>


## Composite rules

Easy Rules allows you to create complex rules from primitive ones. A `CompositeRule` is composed of a set of rules.

This is typically an implementation of the [composite design pattern](http://en.wikipedia.org/wiki/Composite_pattern).

A composite rule is triggered if _all_ conditions of its composing rules are satisfied.
When a composite rule is applied, actions of _all_ composing rules are performed in the natural order of
rules which is rules priorities by default.

To create a composite rule from two primitive rules, you can use the following snippet:

```java
//Create the composite rule from two primitive rules
CompositeRule myCompositeRule =
    new CompositeRule("myCompositeRule", "a composite rule");
myCompositeRule.addRule(myRule1);
myCompositeRule.addRule(myRule2);

//Register the composite rule as a regular rule
RulesEngine rulesEngine = new DefaultRulesEngine();
rulesEngine.registerRule(myCompositeRule);
```

## Rules priorities

Each rule in Easy Rules has a priority. This represents the default order in which registered rules are fired.
 By default, lower values represent higher priorities.
 To override this behavior, you should override the `compareTo` method to provide a custom priority strategy.

* If you decided to extend the `BasicRule` class, you can specify rule priority at construction time or by overriding
the `getPriority()` method

* If your rule is a annotated POJO, you should annotate the method that provides priority with `@Priority` annotation.
This method must be public, have no arguments and return an Integer type
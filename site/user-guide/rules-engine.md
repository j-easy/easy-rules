---
layout: docs
title: Rules engine
header: Rules engine
prev_section: user-guide/defining-rules
next_section: user-guide/managing-rules
doc: true
---

Easy Rules engine holds a registry of rules with unique names. Each instance of Easy Rules engine can be seen as a separate namespace.

Rules are applied according to their natural order.

Easy Rules engine provides the following parameters:

<table>
    <thead>
    <tr class="active">
        <th>Parameter</th>
        <th>Type</th>
        <th>Required</th>
        <th>Default</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>skipOnFirstAppliedRule</td>
        <td>boolean</td>
        <td>no</td>
        <td>false</td>
    </tr>
    <tr>
        <td>rulePriorityThreshold</td>
        <td>int</td>
        <td>no</td>
        <td>Integer.MAX_VALUE</td>
    </tr>
    </tbody>
</table>

The `skipOnFirstAppliedRule` parameter tells the engine to skip next applicable rules when a rule is applied.

The `rulePriorityThreshold` parameters tells the engine to skip next rules if priority exceeds the defined threshold.

You can specify these parameters at rules engine construction time.

## Create a default rules engine

To create a default rules engine and register a rule, use the following snippet:

```java
RulesEngine<Rule> rulesEngine = new DefaultRulesEngine();
rulesEngine.registerRule(myRule);
```

You can fire registered rules as follows:

```java
rulesEngine.fireRules();
```

## Create a annotated rules engine

If your rules are annotated POJOs, you have to use the `AnnotatedRulesEngine` to register them:

```java
AnnotatedRulesEngine rulesEngine = new AnnotatedRulesEngine();
rulesEngine.registerRule(myRule);
```

As with the default engine, you can fire rules using the following snippet :

```java
rulesEngine.fireRules();
```
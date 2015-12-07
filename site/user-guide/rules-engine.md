---
layout: docs
title: Rules engine
header: Rules engine
prev_section: user-guide/defining-rules
next_section: user-guide/managing-rules
doc: true
---

Easy Rules engine holds a registry of rules with unique names. Each instance of Easy Rules engine can be seen as a separate namespace.

Rules are applied according to their natural order (which is priority by default).

## Create a rules engine

To create a rules engine and register a rule, you can use the static method `RulesEngineBuilder.aNewEngineBuilder()`:

```java
RulesEngine rulesEngine = aNewEngineBuilder().build();
rulesEngine.registerRule(myRule);
```

You can then fire registered rules as follows:

```java
rulesEngine.fireRules();
```

## Rules engine parameters

Easy Rules engine can be configured with the following parameters:

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
        <td>rulePriorityThreshold</td>
        <td>int</td>
        <td>no</td>
        <td>Integer.MAX_VALUE</td>
    </tr>
    <tr>
        <td>skipOnFirstAppliedRule</td>
        <td>boolean</td>
        <td>no</td>
        <td>false</td>
    </tr>
    <tr>
        <td>skipOnFirstFailedRule</td>
        <td>boolean</td>
        <td>no</td>
        <td>false</td>
    </tr>
    <tr>
        <td>silentMode</td>
        <td>boolean</td>
        <td>no</td>
        <td>false</td>
    </tr>
    </tbody>
</table>

* The `skipOnFirstAppliedRule` parameter tells the engine to skip next rules when a rule is applied.

* The `skipOnFirstFailedRule` parameter tells the engine to skip next rules when a rule fails.

* The `rulePriorityThreshold` parameter tells the engine to skip next rules if priority exceeds the defined threshold.

* The `silentMode` allows you to mute all loggers when needed.

You can specify these parameters through the `RulesEngineBuilder` API:

```java
RulesEngine rulesEngine = aNewRulesEngine()
    .withRulePriorityThreshold(10)
    .withSkipOnFirstAppliedRule(true)
    .withSkipOnFirstFailedRule(true)
    .withSilentMode(true)
    .build();
```

All parameters are encapsulated in the `RulesEngineParameters` class. If you want to get parameters from your engine,
you can use the following snippet:

```java
RulesEngineParameters parameters = myEngine.getParameters();
```

This allows you to reset the engine parameters after its creation.

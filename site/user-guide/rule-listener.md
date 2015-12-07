---
layout: docs
title: Rule listener
header: Rule listener
prev_section: user-guide/defining-rules
next_section: user-guide/rules-engine
doc: true
---

You can listen to rule execution events through the `RuleListener` API:

```java
public interface RuleListener {
    /**
     * Triggered before a rule is executed.
     */
    void beforeExecute(Rule rule);
    /**
     * Triggered after a rule is executed successfully.
     */
    void onSuccess(Rule rule);
    /**
     * Triggered after a rule is executed with error.
     */
    void onFailure(Rule rule, Exception exception);
}
```

You can implement this interface to provide custom behavior to execute before/after each rule.
To register your listener, use the following snippet:
 
```java
RulesEngine rulesEngine = aNewRulesEngine()
    .withRuleListener(myRuleListener)
    .build();
```

You can register as many listeners as you want, they will be executed in their registration order.

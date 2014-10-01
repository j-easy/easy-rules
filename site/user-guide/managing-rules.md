---
layout: docs
title: Managing rules
header: Managing rules at runtime
prev_section: user-guide/rules-engine
next_section: tutorials/hello-world
doc: true
---

Being able to dynamically reconfigure business rules at runtime in production systems is a recurrent requirement.

Thanks to JMX, Easy Rules can expose rules attributes to be managed via any JMX compliant client.

To make your rule manageable via JMX, you can register it in Easy Rules engine as a JMX managed rule:

```java
RulesEngine rulesEngine = new DefaultRulesEngine();
rulesEngine.registerJmxRule(myRule);
```

This will register your rule as a JMX managed bean with the following object name:

`org.easyrules.core.jmx:type=YourRuleClassName,name=YourRuleName`

By default, rule description and priority are exposed as JMX manageable attributes.
If you need to expose more specific attributes, you can extend the `Rule` interface and add getters and setters of your manageable attributes.

An example of using dynamic rule reconfiguration at runtime is provided in the [online shop tutorial]({{site.url}}/tutorials/dynamic-configuration.html).

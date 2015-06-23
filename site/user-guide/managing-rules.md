---
layout: docs
title: Managing rules
header: Managing rules at runtime
prev_section: user-guide/rules-engine
next_section: user-guide/scheduling-rules-engine
doc: true
---

Being able to dynamically reconfigure business rules at runtime in production systems is a recurrent requirement.

Thanks to JMX, Easy Rules can expose rules attributes to be managed via any JMX compliant client.

First, you need to add the following dependency to your **_pom.xml_**:

```xml
<dependencies>
    <dependency>
        <groupId>org.easyrules</groupId>
        <artifactId>easyrules-jmx</artifactId>
        <version>{{site.version}}</version>
    </dependency>
</dependencies>
```

To make your rule manageable via JMX, it should:

* implement the `JmxRule` interface or extend the `BasicJmxRule` class 
* or be annotated with `javax.management.MXBean` if it is a annotated POJO

Once you defined your rule as a Jmx Compliant object, you can register it in a `JmxRulesEngine` as a managed rule:

```java
JmxRulesEngine rulesEngine = aNewJmxRulesEngine.build();
rulesEngine.registerJmxRule(myJmxCompliantRule);
```

This will register your rule as a JMX managed bean with the following object name:

`org.easyrules.core.jmx:type=YourRuleClassName,name=YourRuleName`

An example of using dynamic rule reconfiguration at runtime is provided in the [online shop tutorial]({{site.url}}/tutorials/dynamic-configuration.html).

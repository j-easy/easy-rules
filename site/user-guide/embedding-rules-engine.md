---
layout: docs
title: Embedding rules engine
header: Embedding rules engine
prev_section: user-guide/scheduling-rules-engine
next_section: tutorials/hello-world
doc: true
---

Easy Rules is a lightweight library that can be used in a standalone Java application or embedded in a web server or a dependency injection container.

As of version {{ site.version }}, Easy Rules provides support for <a href="http://www.spring.io" target="_blank">Spring</a>.
Support for other DI containers will be added in future versions.

In this section, you will learn how to configure rules and rules engine as Spring beans. 
First you need to add the following dependency to your **_pom.xml_**:

```xml
<dependency>
    <groupId>org.easyrules</groupId>
    <artifactId>easyrules-spring</artifactId>
    <version>{{site.version}}</version>
</dependency>
```

Then, you can configure your rules and the rules engine as follows:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- configure rule  -->
    <bean id="rule" class="DummyRule" scope="prototype"/>

    <!-- configure rule listener -->
    <bean id="ruleListener" class="DummyRuleListener"/>

    <!-- configure rules engine -->
    <bean id="rulesEngine" class="org.easyrules.spring.RulesEngineFactoryBean">
        <property name="skipOnFirstAppliedRule" value="true"/>
        <property name="skipOnFirstFailedRule" value="true"/>
        <property name="rulePriorityThreshold" value="10"/>
        <property name="silentMode" value="false"/>
        <property name="rules">
            <list>
                <ref bean="rule"/>
            </list>
        </property>
        <property name="ruleListeners">
            <list>
                <ref bean="ruleListener"/>
            </list>
        </property>
    </bean>

</beans>
```

The `RulesEngineFactoryBean` is responsible for creating rules engine instances.
As you can see, this factory bean is the main entry point to configure:

* Rules
* Rules listeners
* And engine parameters (priority threshold, skipOnFirstAppliedRule, silentMode, etc)

<div id="thread-safety" class="note info">
  <h5>Heads up!</h5>
  <p>If your rules are not thread safe, you should consider make them of scope <strong>prototype</strong>.</p>
  <p>The rules engine instance returned by the <code>RulesEngineFactoryBean</code> is already of scope <strong>prototype</strong>.</p> 
</div>

To get the engine and fires rules, you can use the following snippet:

```java
ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
RulesEngine rulesEngine = (RulesEngine) context.getBean("rulesEngine");

rulesEngine.fireRules();
```

<div class="note">
  <h5>Hint:</h5>
  <p>The main advantage of using Easy Rules with Spring is the ability to register/unregister rules through the Xml configuration 
     without recompiling your application.</p>
</div>


You can find a complete tutorial on how to use Easy Rules with Spring [here]({{site.url}}/tutorials/spring-tutorial.html).
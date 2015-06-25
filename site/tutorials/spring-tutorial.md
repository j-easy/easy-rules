---
layout: docs
title: Using Easy Rules with Spring
header: Using Easy Rules with Spring
prev_section: tutorials/scheduling-engine
next_section: get-involved/release-notes
doc: true
---

In this tutorial, you will learn how to use Easy Rules embedded in a <a href="http://www.spring.io" target="_blank">Spring</a> container.

You will create a dummy rule and a rules engine and configure them as Spring beans. So let's get started.

First you need to add the following dependency to your **_pom.xml_**:

```xml
<dependency>
    <groupId>org.easyrules</groupId>
    <artifactId>easyrules-spring</artifactId>
    <version>{{site.version}}</version>
</dependency>
```

Then, let's create the dummy rule:

```java
@Rule(name = "dummy rule")
public class DummyRule {

    @Condition
    public boolean when() {
        return true;
    }

    @Action
    public void then(){
        System.out.println("Hey, I'm managed by Spring");
    }
}
```

Now, we we can use the `RulesEngineFactoryBean` to configure a rules engine and register the dummy rule:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- configure rule  -->
    <bean id="rule" class="org.easyrules.samples.spring.DummyRule"/>

    <!-- configure rules engine -->
    <bean id="rulesEngine" class="org.easyrules.spring.RulesEngineFactoryBean">
        <property name="rules">
            <list>
                <ref bean="rule"/>
            </list>
        </property>
    </bean>

</beans>
```

Finally, we can get the rules engine from the Spring context and fire rules:

```java
public class Launcher {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("org/easyrules/samples/spring/application-context.xml");
        RulesEngine rulesEngine = (RulesEngine) context.getBean("rulesEngine");

        rulesEngine.fireRules();
    }

}
```

That's all. To run the tutorial, you can follow these instructions from the root directory of Easy Rules:

{% highlight bash %}
$ mvn install
$ cd easyrules-samples
$ mvn exec:java -P runSpringTutorial
{% endhighlight %}

You would get the following output:

```
INFO: Rule priority threshold: 2,147,483,647
INFO: Skip on first applied rule: false
INFO: Skip on first failed rule: false
INFO: Rule 'dummy rule' triggered.
Hey, I'm managed by Spring
INFO: Rule 'dummy rule' performed successfully.
```


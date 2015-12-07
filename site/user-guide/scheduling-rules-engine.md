---
layout: docs
title: Scheduling rules engine
header: Scheduling a rules engine
prev_section: user-guide/managing-rules
next_section: user-guide/embedding-rules-engine
doc: true
---

Easy Rules provides APIs to schedule a rules engine using the popular Java scheduler <a href="http://www.quartz-scheduler.org" target="_blank">Quartz</a>.

To schedule a rules engine instance, first you need to add the following dependency to your **_pom.xml_**:

```xml
<dependency>
    <groupId>org.easyrules</groupId>
    <artifactId>easyrules-quartz</artifactId>
    <version>{{site.version}}</version>
</dependency>
```

Then, you can create a `RulesEngineScheduler` as follows:

```java
RulesEngine rulesEngine = aNewRulesEngine().build();

Date now = new Date();
int everySecond = 1;

RulesEngineScheduler scheduler = RulesEngineScheduler.getInstance();
scheduler.scheduleAtWithInterval(rulesEngine, now, everySecond);
```

This will schedule the rules engine to start now and repeat every second.

The `RulesEngineScheduler` API provides methods to schedule a rules engine:

* At a fixed point of time using `scheduleAt(RulesEngine engine, Date when)`
* Repeatedly with a predefined interval using `scheduleAtWithInterval(RulesEngine engine, Date when, int interval)`
* Using unix cron-like expression with `scheduleCron(RulesEngine engine, String cronExpression)`

To unregister a rules engine, use the following snippet:

```java
scheduler.unschedule(rulesEngine);
```

You can find a tutorial about scheduling rules engine in the [Scheduler tutorial]({{site.url}}/tutorials/scheduler-tutorial.html).

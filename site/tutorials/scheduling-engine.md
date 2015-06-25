---
layout: docs
title: Engine scheduler
header: Engine scheduler
prev_section: tutorials/dynamic-configuration
next_section: tutorials/spring-tutorial
doc: true
---

This tutorial shows how to schedule a rules engine using the `RulesEngineScheduler`.

We would like to print the current time only if the seconds value is even. So let's get started. 

First, we will create a rule that checks the current time and print it to the console if seconds are even:

```java
@Rule(name = "time rule", 
        description = "Print the current time only if seconds are even")
public class TimeRule {

    private Date now;

    @Condition
    public boolean checkTime() {
        now = new Date();
        return now.getSeconds() % 2 == 0;
    }

    @Action
    public void printTime() {
        System.out.println("Seconds in " + now + " are even");
    }

}
```

Then, we have to register an instance of this rule in a Easy Rules engine and schedule the engine:

```java
public class Launcher {

    public static final Date NOW = new Date();
    
    public static final int EVERY_SECOND = 1;

    public static void main(String[] args) throws Exception {

        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .named("time rules engine")
                .withSilentMode(true)
                .build();

        TimeRule timeRule = new TimeRule();
        rulesEngine.registerRule(timeRule);

        RulesEngineScheduler scheduler = RulesEngineScheduler.getInstance();
        scheduler.scheduleAtWithInterval(rulesEngine, NOW, EVERY_SECOND);
        scheduler.start();

        System.out.println("Hit enter to stop the application");
        System.in.read();
        scheduler.stop();
    }
}
```

That's it! The `TimeRule` will be triggered every second and will print the current time if seconds value is even.


To run this tutorial, you can follow these instructions from the root directory of Easy Rules :

{% highlight bash %}
$ mvn install
$ cd easyrules-samples
$ mvn exec:java -P runSchedulerTutorial
{% endhighlight %}

You should see in the console something like:

```
Hit enter to stop the application
Seconds in Tue Jun 23 12:57:32 CEST 2015 are even
Seconds in Tue Jun 23 12:57:34 CEST 2015 are even
Seconds in Tue Jun 23 12:57:36 CEST 2015 are even
Seconds in Tue Jun 23 12:57:38 CEST 2015 are even
[Enter]
```


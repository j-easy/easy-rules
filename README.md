***

<div align="center">
    <b><em>Easy Rules</em></b><br>
    The simple, stupid rules engine for Java&trade;
</div>

<div align="center">

[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT)
[![Build Status](https://github.com/j-easy/easy-rules/workflows/Java%20CI/badge.svg)](https://github.com/j-easy/easy-rules/actions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.jeasy/easy-rules-core/badge.svg?style=flat)](http://search.maven.org/#artifactdetails|org.jeasy|easy-rules-core|4.1.0|)
[![Javadoc](https://www.javadoc.io/badge/org.jeasy/easy-rules-core.svg)](http://www.javadoc.io/doc/org.jeasy/easy-rules-core)
[![Project status](https://img.shields.io/badge/Project%20status-Maintenance-orange.svg)](https://img.shields.io/badge/Project%20status-Maintenance-orange.svg)

</div>

***

## Project status

As of December 2020, Easy Rules is in maintenance mode. This means only bug fixes will be addressed from now on.
Version 4.1.x is the only supported version. Please consider upgrading to this version at your earliest convenience.

## Latest news

* 06/12/2020: Version 4.1 is out with a new module to support [Apache JEXL](https://commons.apache.org/proper/commons-jexl/) as an additional supported expression language! You can find all details about other changes in the [release notes](https://github.com/j-easy/easy-rules/releases).

## What is Easy Rules?

Easy Rules is a Java rules engine inspired by an article called *"[Should I use a Rules Engine?](http://martinfowler.com/bliki/RulesEngine.html)"* of [Martin Fowler](http://martinfowler.com/) in which Martin says:

> You can build a simple rules engine yourself. All you need is to create a bunch of objects with conditions and actions, store them in a collection, and run through them to evaluate the conditions and execute the actions.

This is exactly what Easy Rules does, it provides the `Rule` abstraction to create rules with conditions and actions, and the `RulesEngine` API that runs through a set of rules to evaluate conditions and execute actions.

## Core features

 * Lightweight library and easy to learn API
 * POJO based development with an annotation programming model
 * Useful abstractions to define business rules and apply them easily with Java
 * The ability to create composite rules from primitive ones
 * The ability to define rules using an Expression Language (Like MVEL, SpEL and JEXL)

## Example

### 1. First, define your rule..

#### Either in a declarative way using annotations:

```java
@Rule(name = "weather rule", description = "if it rains then take an umbrella")
public class WeatherRule {

    @Condition
    public boolean itRains(@Fact("rain") boolean rain) {
        return rain;
    }
    
    @Action
    public void takeAnUmbrella() {
        System.out.println("It rains, take an umbrella!");
    }
}
```

#### Or in a programmatic way with a fluent API:

```java
Rule weatherRule = new RuleBuilder()
        .name("weather rule")
        .description("if it rains then take an umbrella")
        .when(facts -> facts.get("rain").equals(true))
        .then(facts -> System.out.println("It rains, take an umbrella!"))
        .build();
```

#### Or using an Expression Language:

```java
Rule weatherRule = new MVELRule()
        .name("weather rule")
        .description("if it rains then take an umbrella")
        .when("rain == true")
        .then("System.out.println(\"It rains, take an umbrella!\");");
```

#### Or using a rule descriptor:

Like in the following `weather-rule.yml` example file:

```yaml
name: "weather rule"
description: "if it rains then take an umbrella"
condition: "rain == true"
actions:
  - "System.out.println(\"It rains, take an umbrella!\");"
```

```java
MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
Rule weatherRule = ruleFactory.createRule(new FileReader("weather-rule.yml"));
```

### 2. Then, fire it!

```java
public class Test {
    public static void main(String[] args) {
        // define facts
        Facts facts = new Facts();
        facts.put("rain", true);

        // define rules
        Rule weatherRule = ...
        Rules rules = new Rules();
        rules.register(weatherRule);

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }
}
```

This is the hello world of Easy Rules. You can find other examples like the [Shop](https://github.com/j-easy/easy-rules/wiki/shop), [Airco](https://github.com/j-easy/easy-rules/wiki/air-conditioning) or [WebApp](https://github.com/j-easy/easy-rules/wiki/web-app) tutorials in the wiki.

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.
Please note that Easy Rules is in [maintenance mode](https://github.com/j-easy/easy-rules#project-status),
which means only pull requests for bug fixes will be considered.

If you believe you found a bug or have any question, please use the [issue tracker](https://github.com/j-easy/easy-rules/issues).

## Awesome contributors

* [Alexey1Gavrilov](https://github.com/Alexey1Gavrilov)
* [andersonkyle](https://github.com/andersonkyle)
* [aston2016](https://github.com/aston2016)
* [avakimov](https://github.com/avakimov)
* [beccagaspard](https://github.com/beccagaspard)
* [bpoussin](https://github.com/bpoussin)
* [cgonul](https://github.com/cgonul)
* [cemo](https://github.com/cemo)
* [dagframstad](https://github.com/dagframstad)
* [danrivcap](https://github.com/danrivcap)
* [Desislav-Petrov](https://github.com/Desislav-Petrov)
* [drem-darios](https://github.com/drem-darios)
* [gs-spadmanabhan](https://github.com/gs-spadmanabhan)
* [JurMarky](https://github.com/JurMarky)
* [jordanjennings](https://github.com/jordanjennings)
* [kayeight](https://github.com/kayeight)
* [khandelwalankit](https://github.com/khandelwalankit)
* [lranasingha](https://github.com/lranasingha)
* [laurikimmel](https://github.com/laurikimmel)
* [mrcritical](https://github.com/mrcritical)
* [paulbrejla](https://github.com/paulbrejla)
* [richdouglasevans](https://github.com/richdouglasevans)
* [ruanjiehui](https://github.com/ruanjiehui)
* [spearway](https://github.com/spearway)
* [stefanbirkner](https://github.com/stefanbirkner)
* [toudidel](https://github.com/toudidel)
* [vinoct6](https://github.com/vinoct6)
* [wg1j](https://github.com/wg1j)
* [will-gilbert](https://github.com/will-gilbert)
* [WayneCui](https://github.com/WayneCui)
* [sanmibuh](https://github.com/sanmibuh)
* [shivmitra](https://github.com/shivmitra)
* [zhhaojie](https://github.com/zhhaojie)

Thank you all for your contributions!

## Easy Rules in other languages

* [EasyRulesGo](https://github.com/CrowdStrike/easyrulesgo) : A port of EasyRules to Golang by [@jiminoc](https://github.com/jiminoc)
* [EasyRulesGroovy](https://github.com/will-gilbert/easyrules-tutorials-groovy) : A port of EasyRules tutorials to Groovy by [@will-gilbert](https://github.com/will-gilbert)
* [Easy-Rules-.NET](https://github.com/skuehlshelby/Easy-Rules-.NET) : A port of EasyRules to .NET Core by [@skuehlshelby](https://github.com/skuehlshelby)
* [EasyRulesCsharp](https://github.com/feldrim/EasyRulesCsharp) : A port of EasyRules to CSharp (WIP) by [@feldrim](https://github.com/feldrim)

## Who is using Easy Rules?

* [Apache Nifi](https://nifi.apache.org) (see [Nifi EasyRules Bundle](https://github.com/apache/nifi/tree/rel/nifi-1.12.1/nifi-nar-bundles/nifi-easyrules-bundle))
* [Open Remote](https://openremote.io) (see [build.gradle](https://github.com/openremote/openremote/blob/v1.0.4/model/build.gradle#L27))
* [Open Smart Register Platform](http://smartregister.org) (see [build.gradle](https://github.com/OpenSRP/opensrp-client-anc/blob/v1.5.0/opensrp-anc/build.gradle#L196))
* [Toad Edge by Quest](https://support.quest.com/fr-fr/technical-documents/toad-edge/2.1/user-guide/14)
* [Extreme Networks](https://cloud.kapostcontent.net/pub/c4c24aae-b82d-44e4-8d86-01c235e4b40f/open-source-declaration-for-extr-xmc-8-dot-5)

## Credits

![YourKit Java Profiler](https://www.yourkit.com/images/yklogo.png)

Many thanks to [YourKit, LLC](https://www.yourkit.com/) for providing a free license of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/index.jsp) to support the development of Easy Rules.

## License

Easy Rules is released under the terms of the MIT license:

```
The MIT License (MIT)

Copyright (c) 2021 Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

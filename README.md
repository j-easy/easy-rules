***

<div align="center">
    <b><em>Easy Rules</em></b><br>
    The simple, stupid rules engine for Java&trade;
</div>

<div align="center">

[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT)
[![Coverage](https://coveralls.io/repos/j-easy/easy-rules/badge.svg?style=flat&branch=master&service=github)](https://coveralls.io/github/j-easy/easy-rules?branch=master)
[![Build Status](https://travis-ci.org/j-easy/easy-rules.svg?branch=master)](https://travis-ci.org/j-easy/easy-rules)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.jeasy/easy-rules-core/badge.svg?style=flat)](http://search.maven.org/#artifactdetails|org.jeasy|easy-rules-core|3.2.0|)
[![Javadoc](https://www.javadoc.io/badge/org.jeasy/easy-rules-core.svg)](http://www.javadoc.io/doc/org.jeasy/easy-rules-core)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/j-easy/easy-rules)

</div>

***

## Latest news

* 09/04/2018: Version 3.2.0 is out with new features and bug fixes. Checkout what's new [here](https://github.com/j-easy/easy-rules/releases).
* 17/12/2017: Version 3.1.0 is out with a [new inference engine](https://github.com/j-easy/easy-rules/wiki/defining-rules-engine) and finally [Expression Language support](https://github.com/j-easy/easy-rules/wiki/expression-language-support)! See all new features and improvements in the [change log](https://github.com/j-easy/easy-rules/releases).
* 01/06/2017: Version 3.0.0 is finally out! See all the new goodies in details [here](https://github.com/j-easy/easy-rules/releases).

## What is Easy Rules?

Easy Rules is a Java rules engine inspired by an article called *"[Should I use a Rules Engine?](http://martinfowler.com/bliki/RulesEngine.html)"* of [Martin Fowler](http://martinfowler.com/) in which Martin says:

> You can build a simple rules engine yourself. All you need is to create a bunch of objects with conditions and actions, store them in a collection, and run through them to evaluate the conditions and execute the actions.

This is exactly what Easy Rules does, it provides the `Rule` abstraction to create rules with conditions and actions, and the `RulesEngine` API that runs through a set of rules to evaluate conditions and execute actions.

## Core features

 * Lightweight library and easy to learn API
 * POJO based development with annotation programming model
 * Useful abstractions to define business rules and apply them easily with Java
 * The ability to create composite rules from primitive ones
 * The ability to define rules using the Expression Language

## Example

### 1. First, define your rule..

#### Either in a declarative way using annotations:

```java
@Rule(name = "weather rule", description = "if it rains then take an umbrella" )
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
Rule weatherRule = MVELRuleFactory.createRuleFrom(new File("weather-rule.yml"));
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

## Installation

<b> Maven </b>
```xml
<dependency>
    <groupId>org.jeasy</groupId>
    <artifactId>easy-rules</artifactId>
    <version>3.2.0</version>
    <type>pom</type>
</dependency>
```

<b>Gradle</b>

implementation 'org.jeasy:easy-rules:3.2.0'

Remember add mavenCentral in the repositories

```
repositories {
        mavenCentral()
}
```

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.

If you found a bug or want to request a feature, please use the [issue tracker](https://github.com/j-easy/easy-rules/issues).

For any further question, you can use the [Gitter](https://gitter.im/j-easy/easy-rules) channel of the project.

## Awesome contributors

* [andersonkyle](https://github.com/andersonkyle)
* [beccagaspard](https://github.com/beccagaspard)
* [bpoussin](https://github.com/bpoussin)
* [cgonul](https://github.com/cgonul)
* [cemo](https://github.com/cemo)
* [dagframstad](https://github.com/dagframstad)
* [danrivcap](https://github.com/danrivcap)
* [drem-darios](https://github.com/drem-darios)
* [gs-spadmanabhan](https://github.com/gs-spadmanabhan)
* [JurMarky](https://github.com/JurMarky)
* [jordanjennings](https://github.com/jordanjennings)
* [khandelwalankit](https://github.com/khandelwalankit)
* [mrcritical](https://github.com/mrcritical)
* [paulbrejla](https://github.com/paulbrejla)
* [richdouglasevans](https://github.com/richdouglasevans)
* [spearway](https://github.com/spearway)
* [toudidel](https://github.com/toudidel)
* [vinoct6](https://github.com/vinoct6)
* [wg1j](https://github.com/wg1j)
* [will-gilbert](https://github.com/will-gilbert)
* [sanmibuh](https://github.com/sanmibuh)
* [shivmitra](https://github.com/sanmibuh)

Thank you all for your contributions!

## Easy Rules in other languages

* [EasyRulesGo](https://github.com/CrowdStrike/easyrulesgo) : A port of EasyRules to Golang by [@jiminoc](https://github.com/jiminoc)
* [EasyRulesGroovy](https://github.com/will-gilbert/easyrules-tutorials-groovy) : A port of EasyRules tutorials to Groovy by [@will-gilbert](https://github.com/will-gilbert)
* [EasyRulesCsharp](https://github.com/feldrim/EasyRulesCsharp) : A port of EasyRules to CSharp (WIP) by [@feldrim](https://github.com/feldrim)

## Credits

|YourKit|Travis CI|
|:-:|:-:|
|![YourKit Java Profiler](https://www.yourkit.com/images/yklogo.png)|![Travis CI](https://travis-ci.com/images/logos/TravisCI-Full-Color.png)|
|Many thanks to [YourKit, LLC](https://www.yourkit.com/) for providing a free license of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/index.jsp) to kindly support the development of Easy Rules.|Many thanks to [Travis CI](https://travis-ci.org) for providing a free continuous integration service for open source projects.|

## License
Easy Rules is released under the terms of the MIT license:

```
The MIT License (MIT)

Copyright (c) 2018 Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)

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

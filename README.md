## What is Easy Rules?

Easy Rules is a Java rules engine inspired by an article called *"[Should I use a Rules Engine?](http://martinfowler.com/bliki/RulesEngine.html)"* of [Martin Fowler](http://martinfowler.com/) in which Martin says:

> You can build a simple rules engine yourself. All you need is to create a bunch of objects with conditions and actions, store them in a collection, and run through them to evaluate the conditions and execute the actions.

This is exactly what Easy Rules does, it provides the `Rule` abstraction to create rules with conditions and actions, and the `RulesEngine` API that runs through a set of rules to evaluate conditions and execute actions.

## Core features

 * Lightweight library and easy to learn API
 * POJO based development with annotation programming model
 * Useful abstractions to define business rules and apply them easily with Java
 * The ability to create composite rules from primitive ones

## Example

##### First, define your rule..

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

##### Then, fire it!

```java
public class Test {
    public static void main(String[] args) {
        // define facts
        Facts facts = new Facts();
        facts.add("rain", true);

        // define rules
        Rules rules = new Rules(new WeatherRule());

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }
}
```

This is the hello world of Easy Rules. You can find other examples like the [FizzBuzz tutorial](https://github.com/j-easy/easy-rules/wiki/fizz-buzz) in the wiki.

## Quick links

|Item                  |Link                                                                                   |
|:---------------------|:--------------------------------------------------------------------------------------|
|Project Home          | [https://github.com/j-easy/easy-rules/wiki](https://github.com/j-easy/easy-rules/wiki)|
|Presentation          | [https://speakerdeck.com/benas/easy-rules](https://speakerdeck.com/benas/easy-rules)  |
|Continuous integration| [![Build Status](https://travis-ci.org/j-easy/easy-rules.svg?branch=master)](https://travis-ci.org/j-easy/easy-rules) |
|Code coverage         | [![Coverage](https://coveralls.io/repos/j-easy/easy-rules/badge.svg?style=flat&branch=master&service=github)](https://coveralls.io/github/j-easy/easy-rules?branch=master) |
|Sonar analysis        | [![Quality Gate](https://sonarqube.com/api/badges/gate?key=org.easyrules:easyrules)](https://sonarqube.com/overview?id=org.easyrules%3Aeasyrules) |

## Current version

* The current stable version is `2.5.0` : [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.easyrules/easyrules-core/badge.svg?style=flat)](http://search.maven.org/#artifactdetails|org.easyrules|easyrules-core|2.5.0|)
* The current development version is `3.0.0-SNAPSHOT`. In order to use the snapshot version, you need to add the following maven repository in your `pom.xml`:

```xml
<repository>
    <id>ossrh</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
</repository>
```

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.

If you found a bug or want to request a feature, please use the [issue tracker](https://github.com/j-easy/easy-rules/issues).

For any further question, you can use the [Gitter](https://gitter.im/j-easy/easy-rules) channel of the project.

## Awesome contributors

* [andersonkyle](https://github.com/andersonkyle)
* [beccagaspard](https://github.com/beccagaspard)
* [cgonul](https://github.com/cgonul)
* [drem-darios](https://github.com/drem-darios)
* [gs-spadmanabhan](https://github.com/gs-spadmanabhan)
* [JurMarky](https://github.com/JurMarky)
* [jordanjennings](https://github.com/jordanjennings)
* [khandelwalankit](https://github.com/khandelwalankit)
* [mrcritical](https://github.com/mrcritical)
* [richdouglasevans](https://github.com/richdouglasevans)
* [spearway](https://github.com/spearway)
* [toudidel](https://github.com/toudidel)
* [vinoct6](https://github.com/vinoct6)
* [will-gilbert](https://github.com/will-gilbert)

Thank you all for your contributions!

## Acknowledgments

|YourKit|Travis CI|
|:-:|:-:|
|![YourKit Java Profiler](https://www.yourkit.com/images/yklogo.png)|![Travis CI](https://cdn.travis-ci.com/images/logos/TravisCI-Full-Color-45e242791b7752b745a7ae53f265acd4.png)|
|Many thanks to [YourKit, LLC](https://www.yourkit.com/) for providing a free license of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/index.jsp) to kindly support the development of Easy Rules.|Many thanks to [Travis CI](https://travis-ci.org) for providing a free continuous integration service for open source projects.|

## License
Easy Rules is released under the [![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT).

```
The MIT License (MIT)

Copyright (c) 2017 Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)

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


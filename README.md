## What is Easy Rules?

Easy Rules is a Java rules engine inspired by an article called *"[Should I use a Rules Engine?](http://martinfowler.com/bliki/RulesEngine.html)"* by [Martin Fowler](http://martinfowler.com/) in which he says:

> You can build a simple rules engine yourself. All you need is to create a bunch of objects with conditions and actions, store them in a collection, and run through them to evaluate the conditions and execute the actions.

This is exactly what Easy Rules does, it provides the `Rule` abstraction to create rules with conditions and actions, and the `RulesEngine` API that runs through a set of rules to evaluate conditions and execute actions.

## Core features

 * Lightweight library and easy to learn API

 * POJO based development with annotation programming model

 * Useful abstractions to define business rules and apply them easily with Java

 * The ability to create composite rules from primitive ones

 * Dynamic rule configuration at runtime using JMX

## Example

##### First, define your rule..

```java
@Rule(name = "my awesome rule" )
public class MyRule {

    @Condition
    public boolean when() {
        return true;
    }
    
    @Action
    public void then() {
        System.out.println("Easy Rules rocks!");
    }
}
```

##### Then, fire it!

```java
public class Test {
    public static void main(String[] args) {
        // create a rules engine
        RulesEngine rulesEngine = aNewRulesEngine().build();
        //register the rule
        rulesEngine.registerRule(new MyRule());
        //fire rules
        rulesEngine.fireRules();
    }
}
```

## Quick links

|Item                  |Link                                                                                  |
|:---------------------|:-------------------------------------------------------------------------------------|
|Project Home          | [http://www.easyrules.org](http://www.easyrules.org)                                 |
|Presentation          | [https://speakerdeck.com/benas/easy-rules](https://speakerdeck.com/benas/easy-rules) |
|Continuous integration| [Build job @ Travis CI](https://travis-ci.org/EasyRules/easyrules)                   |
|Agile Board           | [Backlog items @ waffle.io](https://waffle.io/EasyRules/easyrules)                   |
|Code coverage         | [![Coverage](https://coveralls.io/repos/EasyRules/easyrules/badge.svg?style=flat&branch=master&service=github)](https://coveralls.io/github/EasyRules/easyrules?branch=master) |
|Sonar analysis        | [![Quality Gate](https://sonarqube.com/api/badges/gate?key=org.easyrules:easyrules)](https://sonarqube.com/overview?id=org.easyrules%3Aeasyrules) |
|Dependencies          | [![Dependency Status](https://www.versioneye.com/user/projects/5666159cc3686e000b000923/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5666159cc3686e000b000923) |

## Current version

* The current stable version is `2.3.0` : [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.easyrules/easyrules-core/badge.svg?style=flat)](http://search.maven.org/#artifactdetails|org.easyrules|easyrules-core|2.3.0|)
* The current development version is `2.4.0-SNAPSHOT` : [![Build Status](https://travis-ci.org/EasyRules/easyrules.svg?branch=master)](https://travis-ci.org/EasyRules/easyrules)

In order to use snapshot versions, you need to add the following maven repository in your `pom.xml`:

```xml
<repository>
    <id>ossrh</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
</repository>
```

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.

If you found a bug or want to request a feature, please use the [issue tracker](https://github.com/EasyRules/easyrules/issues).

For any further question, you can use the [Gitter](https://gitter.im/EasyRules/easyrules) channel of the project.

## Awesome contributors

* [beccagaspard](https://github.com/beccagaspard)
* [drem-darios](https://github.com/drem-darios)
* [gs-spadmanabhan](https://github.com/gs-spadmanabhan)
* [mrcritical](https://github.com/mrcritical)
* [spearway](https://github.com/spearway)
* [vinoct6](https://github.com/vinoct6)

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

Copyright (c) 2016 Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)

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


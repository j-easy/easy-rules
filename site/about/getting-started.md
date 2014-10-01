---
layout: docs
title: Getting started
header: Getting started
prev_section: about/overview
next_section: user-guide/introduction
doc: true
---

Easy Rules is a Java library. It requires a Java 1.5+ runtime.

## Building form source

To build Easy Rules from sources, you need to have [git](http://www.git-scm.com) and [maven](http://maven.apache.org/) installed and set up.

Please follow these instructions :

{% highlight bash %}
$ git clone https://github.com/benas/easy-rules.git
$ mvn package
{% endhighlight %}

Easy Rules core jar **_easyrules-core-${version}.jar_** will be generated in the target folder.

## Use with maven

Easy Rules is a single jar file with no dependencies. You have to simply add the jar **_easyrules-core-{{site.version}}.jar_** to your application's classpath.

Using maven, you should add the following dependency to your pom.xml :

```xml
<dependencies>
    <dependency>
        <groupId>org.easyrules</groupId>
        <artifactId>easyrules-core</artifactId>
        <version>{{site.version}}</version>
    </dependency>
</dependencies>
```



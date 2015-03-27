---
layout: docs
title: Getting started
header: Getting started
prev_section: about/overview
next_section: user-guide/introduction
doc: true
---

Easy Rules is a Java library. It requires a Java 1.5+ runtime.

## Building from source

To build Easy Rules from sources, you need to have [git](http://www.git-scm.com) and [maven](http://maven.apache.org/) installed and set up.

Please follow these instructions :

{% highlight bash %}
$ git clone https://github.com/benas/easy-rules.git
$ cd easy-rules
$ mvn package
{% endhighlight %}

Easy Rules core jar **_easyrules-core-{{site.version}}.jar_** will be generated in the **_target_** folder.

## Use with maven

Easy Rules is a single jar file with no dependencies. You have to add the jar **_easyrules-core-{{site.version}}.jar_** to your application's classpath.

If you use maven, you should add the following dependency to your **_pom.xml_** :

```xml
<dependencies>
    <dependency>
        <groupId>org.easyrules</groupId>
        <artifactId>easyrules-core</artifactId>
        <version>{{site.version}}</version>
    </dependency>
</dependencies>
```



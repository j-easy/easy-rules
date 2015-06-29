## What is Easy Rules?

Easy Rules is a simple yet powerful Java Rules Engine providing the following features :

 * Lightweight framework and easy to learn API

 * POJO based development with annotation programming model

 * Useful abstractions to define business rules and apply them easily using Java

 * The ability to create composite rules from primitive ones

 * Dynamic rule reconfiguration at runtime using JMX

## Documentation

The online documentation can be found here : [http://www.easyrules.org](http://www.easyrules.org)

## Presentation

You can find some slides about Easy Rules on [speaker deck](https://speakerdeck.com/benas/easy-rules).

## Continuous integration
[Jenkins job @ cloudbees.com](https://buildhive.cloudbees.com/job/benas/job/easy-rules/)

## Current version

* The current stable version is 2.0.0: [![Build Status](https://buildhive.cloudbees.com/job/benas/job/easy-rules/badge/icon)](https://buildhive.cloudbees.com/job/benas/job/easy-rules/)
* The current development version is 2.1.0-SNAPSHOT. In order to use snapshot versions, you need to add the following maven repository in your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>ossrh</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
```

## Agile Board
[Backlog items @ waffle.io](https://waffle.io/benas/easy-rules)

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.

If you believe you found a bug, please use the [issue tracker](https://github.com/benas/easy-rules/issues).

It would be great to attach a JUnit test that fails and it would be awesome to send a pull request with a patch that fixes the bug!

For any further question, you can use the [Gitter](https://gitter.im/benas/easy-rules) channel of the project: [![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/benas/easy-rules?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Awesome contributors

* [beccagaspard](https://github.com/beccagaspard)
* [drem-darios](https://github.com/drem-darios)
* [gs-spadmanabhan](https://github.com/gs-spadmanabhan)
* [mrcritical](https://github.com/mrcritical)
* [vinoct6](https://github.com/vinoct6)

Thank you all for your contributions!

## Credits

### Yourkit

Many thanks to [YourKit, LLC](https://www.yourkit.com/) for providing a free license of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/index.jsp) to kindly support the development of Easy Rules.

![YourKit Java Profiler](https://www.yourkit.com/images/yklogo.png)

### CloudBees

Many thanks to [CloudBees](https://www.cloudbees.com/) for providing a free [Jenkins](http://jenkins-ci.org/) service to support continuous integration for open source projects.

![CloudBees](https://www.cloudbees.com/sites/default/files/styles/large/public/Button-Powered-by-CB.png)


## License
Easy Rules is released under the [MIT License](http://opensource.org/licenses/mit-license.php/):

```
The MIT License (MIT)

Copyright (c) 2015 Mahmoud Ben Hassine (mahmoud@benhassine.fr)

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


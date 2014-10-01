---
layout: docs
title: Introduction
header: Introduction
prev_section: about/getting-started
next_section: user-guide/defining-rules
doc: true
---

Most business rules can be represented by the following rule definition:

* _Name_: a unique rule name within a rules namespace
* _Description_: a brief description of the rule
* _Priority_: rule priority regarding to other rules
* _Conditions_: set of conditions that should be satisfied to apply the rule
* _Actions_: set of actions to perform when conditions are satisfied

Easy Rules provides an abstraction for each of these key points that define a business rule.

Next section will show you how to define a rule in Easy Rules either by extending a base class or by annotating your
own domain class.

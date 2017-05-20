package org.jeasy.rules.tutorials.helloworld

import Action
import Condition
import Rule

@Rule(name = "Hello World rule", description = "Always say hello world")
class HelloWorldRule {

    @Condition
    boolean when() { true }

    @Action
    def then() throws Exception { println 'hello world' }

}

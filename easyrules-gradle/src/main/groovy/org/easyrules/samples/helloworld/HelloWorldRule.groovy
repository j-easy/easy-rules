package org.easyrules.samples.helloworld

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule

@Rule(name = "Hello World rule", description = "Say Hello to duke's friends only")
class HelloWorldRule {

    def input

    // The rule should be applied only if the user's response is yes (Duke's friend)
    @Condition
    boolean when() { input.equalsIgnoreCase 'yes' }

    // When rule conditions are satisfied, print 'Hello duke's friend!' to the console
    @Action
    def then() throws Exception { println 'Hello Duke\'s friend!' }

}

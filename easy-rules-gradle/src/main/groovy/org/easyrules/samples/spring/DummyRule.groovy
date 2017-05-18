package org.easyrules.samples.spring

import Action
import Condition
import Rule

@Rule(name = "dummy rule")
class DummyRule {

    @Condition
    boolean when() { true }

    @Action
    def then() { println 'Hey, I\'m managed by Spring'}
    
}

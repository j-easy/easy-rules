package org.easyrules.samples.spring

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule

@Rule(name = "dummy rule")
class DummyRule {

    @Condition
    boolean when() { true }

    @Action
    def then() { println 'Hey, I\'m managed by Spring'}
    
}

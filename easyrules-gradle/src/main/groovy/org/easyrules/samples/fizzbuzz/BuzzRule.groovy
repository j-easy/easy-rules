package org.easyrules.samples.fizzbuzz

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Priority
import org.easyrules.annotation.Rule

@Rule
class BuzzRule {

    def input

    @Condition
    boolean when() { input % 5 == 0 }

    @Action
    def then() { print 'buzz' }

    @Priority
    int getPriority() { 2 }
}

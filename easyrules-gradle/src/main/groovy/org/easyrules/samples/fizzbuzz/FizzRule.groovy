package org.easyrules.samples.fizzbuzz

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Priority
import org.easyrules.annotation.Rule

@Rule
class FizzRule {

    def input

    @Condition
    boolean when() { input % 3 == 0 }

    @Action
    def then() { print 'fizz' }

    @Priority
    int getPriority() { 1 }
}

package org.easyrules.samples.fizzbuzz

import Action
import Condition
import Priority
import Rule

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

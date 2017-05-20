package org.easyrules.samples.fizzbuzz

import Action
import Condition
import Priority
import Rule

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

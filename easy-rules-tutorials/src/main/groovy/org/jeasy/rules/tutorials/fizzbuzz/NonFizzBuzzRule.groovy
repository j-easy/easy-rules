package org.easyrules.samples.fizzbuzz

import Action
import Condition
import Priority
import Rule

@Rule
class NonFizzBuzzRule {

    def input

    @Condition
    boolean when() { input % 5 != 0 || input % 7 != 0 }

    @Action
    def then() { print input }

    @Priority
    int getPriority() { 3 }
}

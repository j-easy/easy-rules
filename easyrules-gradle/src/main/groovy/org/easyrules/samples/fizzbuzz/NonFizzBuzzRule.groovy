package org.easyrules.samples.fizzbuzz

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Priority
import org.easyrules.annotation.Rule

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

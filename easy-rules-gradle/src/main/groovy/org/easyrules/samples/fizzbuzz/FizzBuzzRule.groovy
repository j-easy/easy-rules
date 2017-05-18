package org.easyrules.samples.fizzbuzz

import CompositeRule

class FizzBuzzRule extends CompositeRule {

    FizzBuzzRule(Object... rules) {
    	rules.each {  addRule it  }
    }

    @Override
    int getPriority() { 0 }
}

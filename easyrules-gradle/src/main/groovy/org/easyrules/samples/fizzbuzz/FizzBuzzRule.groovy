package org.easyrules.samples.fizzbuzz

import org.easyrules.core.CompositeRule

class FizzBuzzRule extends CompositeRule {

    FizzBuzzRule(Object... rules) {
    	rules.each {  addRule it  }
    }

    @Override
    int getPriority() { 0 }
}

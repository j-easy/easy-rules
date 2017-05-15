package org.easyrules.samples.shop

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.core.BasicRule

class UnderAgeRule extends BasicRule {

    def person

    UnderAgeRule(person) {
        super('UnderAgeRule', 'Children are not allowed to buy alcohol', 2)
        this.person = person
    }

    @Condition
    boolean evaluate() {
        person.adult == false
    }

    @Action
    void execute(){
        println "Shop Owner: Sorry $person.name, you are not allowed to buy alcohol."
    }

}

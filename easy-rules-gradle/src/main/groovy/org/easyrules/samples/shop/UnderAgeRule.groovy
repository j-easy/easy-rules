package org.easyrules.samples.shop

import Action
import Condition
import BasicRule

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

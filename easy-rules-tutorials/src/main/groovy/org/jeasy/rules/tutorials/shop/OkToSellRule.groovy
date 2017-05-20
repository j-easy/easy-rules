package org.easyrules.samples.shop

import Action
import Condition
import BasicRule

class OkToSellRule extends BasicRule {

    def person

    OkToSellRule(person) {
        super('OkToSellRule', 'Adults are allowed to buy alcohol', 2)
        this.person = person
    }

    @Condition
    boolean evaluate() {
        person.adult == true
    }

    @Action
    void execute(){
        println "Shop Owner: Ok, $person.name, here you go."
    }

}

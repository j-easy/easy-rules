package org.easyrules.samples.shop

import org.easyrules.core.BasicRule

public class AgeRule extends BasicRule {

    private static final int ADULT_AGE = 21

    def person

    public AgeRule(person) {
        super('AgeRule', "Check if person's age is $ADULT_AGE or over and marks the person as adult", 1)
        this.person = person
    }

    @Override
    public boolean evaluate() {
        return person.age >= ADULT_AGE
    }

    @Override
    public void execute() {
        person.adult  = true
        println "Person $person.name has been marked as adult"
    }
    
}

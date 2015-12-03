package org.easyrules.samples.shop.part1;

import org.easyrules.core.BasicRule;
import org.easyrules.samples.shop.Person;

public class AgeRule extends BasicRule {

    private static final int ADULT_AGE = 18;

    private Person person;

    public AgeRule(Person person) {
        super("AgeRule", "Check if person's age is > 18 and marks the person as adult", 1);
        this.person = person;
    }

    @Override
    public boolean evaluate() {
        return person.getAge() > ADULT_AGE;
    }

    @Override
    public void execute() {
        person.setAdult(true);
        System.out.printf("Person %s has been marked as adult", person.getName());
        System.out.println();
    }
    
}

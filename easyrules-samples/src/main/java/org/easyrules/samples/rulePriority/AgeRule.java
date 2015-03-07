package org.easyrules.samples.rulePriority;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

/**
 * A rule class that marks a person as adult if it's age is greater than 18.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@Rule(name = "AgeRule", description = "Check if person's age is > 18 and marks the person as adult")
public class AgeRule {

    private Person person;

    private int adultAge = 18;

    public AgeRule(Person person) {
        this.person = person;
    }

    @Condition
    public boolean isAdult() {
        return person.getAge() > adultAge;
    }

    @Action
    public void markAsAdult(){
        person.setAdult(true);
        System.out.printf("Person %s has been marked as adult.\n", person.getName());
    }

    @Priority
    public int getPriority() {
        return 1;
    }

}

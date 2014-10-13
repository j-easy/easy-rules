package org.easyrules.samples.rulePriority;

import org.easyrules.core.AnnotatedRulesEngine;

/**
 * Launcher class for rule priority tutorial.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class Launcher {

    public static void main(String[] args) {

        //create a person instance
        Person tom = new Person("Tom", 16);
        System.out.println("Tom: Hi! can I have some Vodka please?");

        //create a rules engine
        AnnotatedRulesEngine annotatedRulesEngine = new AnnotatedRulesEngine();

        //register rules
        annotatedRulesEngine.registerRule(new AgeRule(tom));
        annotatedRulesEngine.registerRule(new AlcoholRule(tom));

        //fire rules
        annotatedRulesEngine.fireRules();

    }

}

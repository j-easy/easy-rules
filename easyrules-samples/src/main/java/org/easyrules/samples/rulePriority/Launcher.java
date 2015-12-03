package org.easyrules.samples.rulePriority;

import org.easyrules.api.RulesEngine;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

/**
 * Launcher class for rule priority tutorial.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class Launcher {

    public static void main(String[] args) {

        //create a person instance
        Person tom = new Person("Tom", 16);
        System.out.println("Tom: Hi! can I have some Vodka please?");

        //create a rules engine
        RulesEngine rulesEngine = aNewRulesEngine().build();

        //register rules
        rulesEngine.registerRule(new AgeRule(tom));
        rulesEngine.registerRule(new AlcoholRule(tom));

        //fire rules
        rulesEngine.fireRules();

    }

}

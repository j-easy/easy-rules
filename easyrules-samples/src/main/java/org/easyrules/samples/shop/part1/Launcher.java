package org.easyrules.samples.shop.part1;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import org.easyrules.api.RulesEngine;
import org.easyrules.samples.shop.Person;

public class Launcher {

    public static void main(String[] args) {
        //create a person instance
        Person tom = new Person("Tom", 14);
        System.out.println("Tom: Hi! can I have some Vodka please?");

        //create a rules engine
        RulesEngine rulesEngine = aNewRulesEngine()
                .named("shop rules engine")
                .build();

        //register rules
        rulesEngine.registerRule(new AgeRule(tom));
        rulesEngine.registerRule(new AlcoholRule(tom));

        //fire rules
        rulesEngine.fireRules();
    }

}

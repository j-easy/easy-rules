package org.easyrules.samples.shop.part2;

import static org.easyrules.core.JmxRulesEngineBuilder.aNewJmxRulesEngine;

import java.util.Scanner;
import org.easyrules.api.JmxRulesEngine;
import org.easyrules.samples.shop.Person;
import org.easyrules.samples.shop.part1.AlcoholRule;

public class Launcher {

    public static void main(String[] args) {

        //create a person instance
        Person tom = new Person("Tom", 14);
        System.out.println("Tom: Hi! can I have some Vodka please?");

        //create a Jmx rules engine
        JmxRulesEngine rulesEngine = aNewJmxRulesEngine()
                .named("shop rules engine")
                .build();

        //register rules
        rulesEngine.registerJmxRule(new AgeRule(tom));
        rulesEngine.registerRule(new AlcoholRule(tom));

        //fire rules
        rulesEngine.fireRules();

        // Update adult age via a JMX client.
        Scanner scanner = new Scanner(System.in);
        System.out.println("******************************************************");
        System.out.println("Change adult age via a JMX client and then press enter");
        scanner.nextLine();

        System.out.println("*****************************************");
        System.out.println("Re fire rules after updating adult age...");
        System.out.println("******************************************");

        rulesEngine.fireRules();
    }

}

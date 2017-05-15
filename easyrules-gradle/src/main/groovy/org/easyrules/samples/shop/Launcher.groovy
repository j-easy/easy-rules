package org.easyrules.samples.shop

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine

import org.easyrules.api.RulesEngine

class Launcher {

    static void main(String... args) {

        println "${'='*90}"
        println '     S H O P   W I T H   E A S Y   R U L E S '
        println "${'='*90}"


        // Create a person instance
        Person person = new Person(name:args[0], age: args[1] as int)
        println "$person.name: Hi! can I have some Vodka please?"

        // Create a rules engine
        RulesEngine rulesEngine = aNewRulesEngine()
                .named("shop rules engine")
                .build()

        // Register rules
        rulesEngine.registerRule(new AgeRule(person))
        rulesEngine.registerRule(new OkToSellRule(person))
        rulesEngine.registerRule(new UnderAgeRule(person))

        // Fire rules
        rulesEngine.fireRules()
    }

}

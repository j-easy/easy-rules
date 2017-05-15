package org.easyrules.samples.fizzbuzz

import org.easyrules.core.RulesEngineBuilder

 class FizzBuzzWithEasyRules {

    static void main(String... args) {

        def label = 'FIZZBUZZ WITH EASYRULES'.replaceAll(/./){it+' '}
        def width = 80

        println """${'='*width}
                  |${label.center width }
                  |${'='*width}""".stripMargin()

        // Create rules engine
        def fizzBuzzEngine = RulesEngineBuilder.aNewRulesEngine()
                .withSkipOnFirstAppliedRule(true)
                .withSilentMode(true)
                .build()

        // Create rules
        def fizzRule = new FizzRule()
        def buzzRule = new BuzzRule()
        def nonFizzBuzzRule = new NonFizzBuzzRule()
        def fizzBuzzRule = new FizzBuzzRule(fizzRule, buzzRule)

        // Register all rules
        fizzBuzzEngine.registerRule(fizzRule)
        fizzBuzzEngine.registerRule(buzzRule)
        fizzBuzzEngine.registerRule(fizzBuzzRule)
        fizzBuzzEngine.registerRule(nonFizzBuzzRule)

        // Fire rules
        (1..100).each { input ->
            fizzRule.input = input
            buzzRule.input = input
            nonFizzBuzzRule.input = input
            fizzBuzzEngine.fireRules()
            println()
        }
    }
}

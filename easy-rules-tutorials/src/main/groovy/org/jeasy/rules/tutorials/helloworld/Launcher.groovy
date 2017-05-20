package org.jeasy.rules.tutorials.helloworld

import static RulesEngineBuilder.aNewRulesEngine
import org.jeasy.rules.api.Facts
import org.jeasy.rules.api.Rules

import java.util.Scanner

class Launcher {

    static void main(String... args) {

        def label = 'HELLO WORLD EXAMPLE'.replaceAll(/./){it+' '}
        def width = 80

        println """${'='*width}
                  |${label.center width }
                  |${'='*width}""".stripMargin()

        // define rules
        def rule = new HelloWorldRule()
        def rules = new Rules()
        rules.register(rule)

        // define facts
        def facts = new Facts()

        // fire rules on known facts
        def rulesEngine = aNewRulesEngine().build()
        rulesEngine.fire(rules, facts)

    }
}

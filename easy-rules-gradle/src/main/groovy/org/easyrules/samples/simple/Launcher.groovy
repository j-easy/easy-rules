package org.easyrules.samples.simple

import static RulesEngineBuilder.aNewRulesEngine

class Launcher {

    static void main(String... args) {

        def label = 'FIRE A BASIC RULE'.replaceAll(/./){it+' '}
        def width = 80

        println """${'='*width}
                  |${label.center width }
                  |${'='*width}""".stripMargin()

        // Create a rules engine
        def rulesEngine = aNewRulesEngine().build()

        // Register rules
        rulesEngine.registerRule(new SimpleRule())

        // Fire rules
        rulesEngine.fireRules()
    }

}

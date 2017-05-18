package org.easyrules.samples.helloworld

import static RulesEngineBuilder.aNewRulesEngine
import java.util.Scanner

class Launcher {

    static void main(String... args) {

        def label = 'HELLO WORLD EXAMPLE'.replaceAll(/./){it+' '}
        def width = 80

        println """${'='*width}
                  |${label.center width }
                  |${'='*width}""".stripMargin()

        def scanner = new Scanner(System.in)
        print("Are you a friend of Duke? [yes/no]: ")
        def input = scanner.nextLine()

        def rule = new HelloWorldRule()
        rule.input = input.trim()
    
        def rulesEngine = aNewRulesEngine().build()
        rulesEngine.registerRule(rule)
        rulesEngine.fireRules()

    }
}

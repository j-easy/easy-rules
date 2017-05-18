package org.easyrules.samples.spring

import RulesEngine
import org.springframework.context.support.ClassPathXmlApplicationContext

class Launcher {

    static void main(String... args) throws Exception {

        def label = 'SPRING INJECTING EASY RULES'.replaceAll(/./){it+' '}
        def width = 80

        println """${'='*width}
                  |${label.center width }
                  |${'='*width}""".stripMargin()


        def context = new ClassPathXmlApplicationContext("org/easyrules/samples/spring/application-context.xml")
        def rulesEngine = (RulesEngine) context.getBean("rulesEngine")

        rulesEngine.fireRules()
    }

}

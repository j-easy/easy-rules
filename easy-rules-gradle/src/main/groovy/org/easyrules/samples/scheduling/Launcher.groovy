package org.easyrules.samples.scheduling

import RulesEngineBuilder
import org.easyrules.quartz.RulesEngineScheduler

import java.util.Date

class Launcher {

    static final Date NOW = new Date()

    static final int EVERY_SECOND = 1

    static void main(String... args) throws Exception {

        def label = 'SCHEDULING WITH EASY RULES'.replaceAll(/./){it+' '}
        def width = 80

        println """${'='*width}
                  |${label.center width }
                  |${'='*width}""".stripMargin()

        def rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .named("time rules engine")
                .withSilentMode(true)
                .build()

        def timeRule = new TimeRule()
        rulesEngine.registerRule(timeRule)

        def scheduler = RulesEngineScheduler.getInstance()
        scheduler.scheduleAtWithInterval(rulesEngine, NOW, EVERY_SECOND)
        scheduler.start()

        println '\nPress "Return" to stop the application\n'
        System.in.read()
        scheduler.stop()
    }
}

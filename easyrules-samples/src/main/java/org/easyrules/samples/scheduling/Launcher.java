package org.easyrules.samples.scheduling;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.easyrules.quartz.RulesEngineScheduler;

import java.util.Date;

/**
 * Main class to run the scheduler tutorial.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static final Date NOW = new Date();

    public static final int EVERY_SECOND = 1;

    public static void main(String[] args) throws Exception {

        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .named("time rules engine")
                .withSilentMode(true)
                .build();

        TimeRule timeRule = new TimeRule();
        rulesEngine.registerRule(timeRule);

        RulesEngineScheduler scheduler = RulesEngineScheduler.getInstance();
        scheduler.scheduleAtWithInterval(rulesEngine, NOW, EVERY_SECOND);
        scheduler.start();

        System.out.println("Hit enter to stop the application");
        System.in.read();
        scheduler.stop();
    }
}

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

    public static void main(String[] args) throws Exception {

        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();

        rulesEngine.registerRule(new TimeRule());

        RulesEngineScheduler scheduler = new RulesEngineScheduler(rulesEngine);
        scheduler.scheduleAtWithInterval(new Date(), 1);
        scheduler.start();

    }
}

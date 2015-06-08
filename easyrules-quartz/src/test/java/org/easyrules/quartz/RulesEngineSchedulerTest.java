package org.easyrules.quartz;

import org.apache.commons.lang3.time.DateUtils;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link RulesEngineScheduler}.
 *
 * Created by Sunand on 6/8/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class RulesEngineSchedulerTest {

    private RulesEngineScheduler rulesEngineScheduler;

    @Test(timeout = 20000)
    public void testJobScheduling() throws Exception {

        final HelloWorldRule helloWorldRule = new HelloWorldRule();
        helloWorldRule.setInput("yes");

        RulesEngine engine = RulesEngineBuilder.aNewRulesEngine().build();
        engine.registerRule(helloWorldRule);

        rulesEngineScheduler = new RulesEngineScheduler();
        Date futureDate = DateUtils.addSeconds(new Date(), 3);
        rulesEngineScheduler.scheduleAt(futureDate);
        rulesEngineScheduler.start(engine);

        assertThat(rulesEngineScheduler.isStarted()).isTrue();

        Thread.sleep(5000);
    }

    @Test
    public void testMultipleJobsScheduling() throws Exception {
        NumberPrintRule rule1 = new NumberPrintRule(10);

        RulesEngine engine1 = RulesEngineBuilder.aNewRulesEngine().build();
        engine1.registerRule(rule1);

        RulesEngineScheduler scheduler1 = new RulesEngineScheduler();
        Date futureDate1 = DateUtils.addSeconds(new Date(), 3);
        scheduler1.scheduleAt(futureDate1);
        scheduler1.start(engine1);

        PrintRule rule2 = new PrintRule(20);

        RulesEngine engine2 = RulesEngineBuilder.aNewRulesEngine().withSkipOnFirstAppliedRule(true).build();
        engine2.registerRule(rule2);

        RulesEngineScheduler scheduler2 = new RulesEngineScheduler();
        Date futureDate2 = DateUtils.addSeconds(new Date(), 3);
        scheduler2.scheduleAt(futureDate2);
        scheduler2.start(engine2);

        assertThat(scheduler1.isStarted()).isTrue();
        assertThat(scheduler2.isStarted()).isTrue();
        Thread.sleep(5000);

        scheduler1.stop();
        scheduler2.stop();
        assertThat(scheduler1.isStopped()).isTrue();
        assertThat(scheduler2.isStopped()).isTrue();
    }

    @After
    public void tearDown() throws Exception {
        if(rulesEngineScheduler != null) {
            rulesEngineScheduler.stop();
        }
    }

}

package org.easyrules.quartz;

import org.easyrules.api.RulesEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link RulesEngineScheduler}.
 *
 * Created by Sunand on 6/8/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class RulesEngineSchedulerTest {

    private static final Date now = new Date();

    private static final int everyMinute = 1;

    @Mock
    private RulesEngine engine1, engine2;

    @Before
    public void setUp() throws Exception {
        when(engine1.getName()).thenReturn("engine1");
        when(engine2.getName()).thenReturn("engine2");
    }

    @Test
    public void testJobScheduling() throws Exception {

        RulesEngineScheduler rulesEngineScheduler = new RulesEngineScheduler(engine1);
        rulesEngineScheduler.scheduleAtWithInterval(now, everyMinute);
        rulesEngineScheduler.start();

        assertThat(rulesEngineScheduler.isStarted()).isTrue();
        verify(engine1).fireRules();

        rulesEngineScheduler.stop();
        assertThat(rulesEngineScheduler.isStopped()).isTrue();

    }

    @Test
    public void testMultipleJobsScheduling() throws Exception {

        RulesEngineScheduler rulesEngineScheduler1 = new RulesEngineScheduler(engine1);
        rulesEngineScheduler1.scheduleAtWithInterval(now, everyMinute);
        rulesEngineScheduler1.start();

        RulesEngineScheduler rulesEngineScheduler2 = new RulesEngineScheduler(engine2);
        rulesEngineScheduler2.scheduleAtWithInterval(now, everyMinute);
        rulesEngineScheduler2.start();

        assertThat(rulesEngineScheduler1).isNotEqualTo(rulesEngineScheduler2);

        assertThat(rulesEngineScheduler1.isStarted()).isTrue();
        assertThat(rulesEngineScheduler2.isStarted()).isTrue();

        verify(engine1).fireRules();
        verify(engine2).fireRules();

        rulesEngineScheduler1.stop();
        rulesEngineScheduler2.stop();

    }

}

package org.easyrules.quartz;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link RulesEngineScheduler}.
 *
 * Created by Sunand on 6/8/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class RulesEngineSchedulerTest {

    private static final Date now = new Date();

    private RulesEngineScheduler rulesEngineScheduler;

    @Mock
    private RulesEngine engine1, engine2;
    
    @Mock
    private RulesEngineParameters parameters1, parameters2;

    @Before
    public void setUp() throws Exception {
        rulesEngineScheduler = RulesEngineScheduler.getInstance();

        when(engine1.getParameters()).thenReturn(parameters1);
        when(parameters1.getName()).thenReturn("engine1");
        when(engine2.getParameters()).thenReturn(parameters2);
        when(parameters2.getName()).thenReturn("engine2");
    }

    @Test
    public void testJobScheduling() throws Exception {
        rulesEngineScheduler.scheduleAt(engine1, now);
        rulesEngineScheduler.scheduleAt(engine2, now);

        assertThat(rulesEngineScheduler.isScheduled(engine1)).isTrue();
        assertThat(rulesEngineScheduler.isScheduled(engine2)).isTrue();

        rulesEngineScheduler.start();
        assertThat(rulesEngineScheduler.isStarted()).isTrue();

        Thread.sleep(500); // sleep to ensure the next verify is called after calling the engine

        verify(engine1).fireRules();
        verify(engine2).fireRules();

        rulesEngineScheduler.unschedule(engine1);
        assertThat(rulesEngineScheduler.isScheduled(engine1)).isFalse();

        rulesEngineScheduler.unschedule(engine2);
        assertThat(rulesEngineScheduler.isScheduled(engine2)).isFalse();

        verify(engine1, times(4)).getParameters();
        verify(engine2, times(4)).getParameters();

        rulesEngineScheduler.stop();
        assertThat(rulesEngineScheduler.isStopped()).isTrue();
    }

}

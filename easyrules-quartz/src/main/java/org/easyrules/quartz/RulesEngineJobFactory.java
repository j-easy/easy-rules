package org.easyrules.quartz;

import org.easyrules.api.RulesEngine;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

/**
 * Quartz Job factory implementation used to create Rules Engine job instances.
 *
 * Created by Sunand on 6/8/2015.
 */
public class RulesEngineJobFactory implements JobFactory {

    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        return new RulesEngineJob((RulesEngine) triggerFiredBundle.getJobDetail().getJobDataMap().get("engine"));
    }
}

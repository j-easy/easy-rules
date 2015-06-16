/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package org.easyrules.quartz;

import org.easyrules.api.RulesEngine;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * Quartz scheduler wrapper used to setup triggers.
 *
 * Created by Sunand on 6/8/2015.
 */
public class RulesEngineScheduler {

    private static final Logger LOGGER = Logger.getLogger(RulesEngineScheduler.class.getName());

    /**
     * The name of job trigger.
     */
    private String triggerName;

    /**
     * The name of job.
     */
    private String jobName;

    /**
     * The trigger used to fire rules execution.
     */
    private Trigger trigger;

    /**
     * Quartz scheduler.
     */
    private Scheduler scheduler;

    /**
     * The engine to schedule.
     */
    private RulesEngine engine;

    public RulesEngineScheduler(RulesEngine engine) throws RulesEngineSchedulerException {
        this.engine = engine;
        JobFactory jobFactory = new RulesEngineJobFactory();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.setJobFactory(jobFactory);
            jobName = "re-job-" + engine.getName();
            triggerName = "trigger-for-" + jobName;
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("An exception occurred during scheduler setup", e);
        }
    }

    /**
     * Setup the time the trigger should start at.
     *
     * @param startTime the start time
     */
    public void scheduleAt(final Date startTime) {
        trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName)
                .startAt(startTime)
                .forJob(jobName)
                .build();
        LOGGER.log(Level.INFO, "Building a scheduler for job {0} to start at {1}", new Object[]{jobName, startTime});
    }

    /**
     * Setup a trigger to start at a fixed point of time and repeat with interval period.
     *
     * @param startTime the start time
     * @param interval  the repeat interval in seconds
     */
    public void scheduleAtWithInterval(final Date startTime, final int interval) {
        ScheduleBuilder scheduleBuilder = simpleSchedule()
                .withIntervalInSeconds(interval)
                .repeatForever();
        trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName)
                .startAt(startTime)
                .withSchedule(scheduleBuilder)
                .forJob(jobName)
                .build();
        LOGGER.log(Level.INFO, "Building a scheduler for job {0} to start at {1} and every {2} second(s)", new Object[]{jobName, startTime, interval});
    }

    /**
     * Setup a unix cron-like trigger.
     *
     * @param cronExpression the cron expression to use.
     * For a complete tutorial about cron expressions, please refer to
     * <a href="http://quartz-scheduler.org/documentation/quartz-2.1.x/tutorials/crontrigger">quartz reference documentation</a>.
     */
    public void scheduleCron(final String cronExpression) {
        trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .forJob(jobName)
                .build();
        LOGGER.log(Level.INFO, "Building a scheduler for job {0} with cron expression {1}", new Object[]{jobName, cronExpression});
    }

    /**
     * Start the scheduler.
     *
     * @throws RulesEngineSchedulerException thrown if the scheduler cannot be started
     */
    public void start() throws RulesEngineSchedulerException {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("engine", engine);
            JobDetail job = JobBuilder.newJob(RulesEngineJob.class).withIdentity(jobName).usingJobData(jobDataMap).build();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("An exception occurred during scheduler startup", e);
        }
    }


    /**
     * Stop the scheduler.
     *
     * Note: The scheduler cannot be re-started.
     *
     * @throws RulesEngineSchedulerException thrown if the scheduler cannot be stopped
     */
    public void stop() throws RulesEngineSchedulerException {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("An exception occurred during scheduler shutdown", e);
        }
    }

    /**
     * Check if the scheduler is started.
     *
     * @throws RulesEngineSchedulerException thrown if the scheduler status cannot be checked
     */
    public boolean isStarted() throws RulesEngineSchedulerException {
        try {
            return scheduler.isStarted();
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("An exception occurred during checking if the scheduler is started", e);
        }
    }

    /**
     * Check if the scheduler is stopped.
     *
     * @throws RulesEngineSchedulerException thrown if the scheduler status cannot be checked
     */
    public boolean isStopped() throws RulesEngineSchedulerException {
        try {
            return scheduler.isShutdown();
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("An exception occurred during checking if the scheduler is stopped", e);
        }
    }

}

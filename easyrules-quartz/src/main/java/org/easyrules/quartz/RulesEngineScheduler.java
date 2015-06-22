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

import static org.easyrules.util.Utils.checkNotNull;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Quartz scheduler wrapper used to setup triggers.
 * <p/>
 * Created by Sunand on 6/8/2015.
 */
public class RulesEngineScheduler {

    private static final Logger LOGGER = Logger.getLogger(RulesEngineScheduler.class.getName());

    private static final String JOB_NAME_PREFIX = "re-";

    private static final String TRIGGER_NAME_PREFIX = "trigger-for-re-";

    /**
     * Quartz scheduler.
     */
    private Scheduler scheduler;

    /**
     * The scheduler singleton instance.
     */
    private static RulesEngineScheduler instance;

    /**
     * Get the scheduler instance.
     *
     * @return The scheduler instance
     * @throws RulesEngineSchedulerException thrown if an exception occurs while creating the scheduler
     */
    public static RulesEngineScheduler getInstance() throws RulesEngineSchedulerException {
        if (instance == null) {
            instance = new RulesEngineScheduler();
        }
        return instance;
    }

    RulesEngineScheduler() throws RulesEngineSchedulerException {
        JobFactory jobFactory = new RulesEngineJobFactory();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.setJobFactory(jobFactory);
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("An exception occurred during scheduler setup", e);
        }
    }

    /**
     * Schedule an engine to start at a fixed point of time.
     *
     * @param engine    the engine to schedule
     * @param startTime the start time
     */
    public void scheduleAt(final RulesEngine engine, final Date startTime) throws RulesEngineSchedulerException {
        checkNotNull(engine, "engine");
        checkNotNull(startTime, "startTime");

        String engineName = engine.getName();
        String jobName = JOB_NAME_PREFIX + engineName;
        String triggerName = TRIGGER_NAME_PREFIX + engineName;

        Trigger trigger = newTrigger()
                .withIdentity(triggerName)
                .startAt(startTime)
                .forJob(jobName)
                .build();

        JobDetail job = getJobDetail(engine, jobName);

        try {
            LOGGER.log(Level.INFO, "Scheduling engine ''{0}'' to start at {1}", new Object[]{engine, startTime});
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("Unable to schedule engine " + engine, e);
        }
    }

    /**
     * Schedule an engine to start at a fixed point of time and repeat with interval period.
     *
     * @param engine    the engine to schedule
     * @param startTime the start time
     * @param interval  the repeat interval in seconds
     */
    public void scheduleAtWithInterval(final RulesEngine engine, final Date startTime, final int interval) throws RulesEngineSchedulerException {
        checkNotNull(engine, "engine");
        checkNotNull(startTime, "startTime");

        String engineName = engine.getName();
        String jobName = JOB_NAME_PREFIX + engineName;
        String triggerName = TRIGGER_NAME_PREFIX + engineName;

        ScheduleBuilder scheduleBuilder = simpleSchedule()
                .withIntervalInSeconds(interval)
                .repeatForever();

        Trigger trigger = newTrigger()
                .withIdentity(triggerName)
                .startAt(startTime)
                .withSchedule(scheduleBuilder)
                .forJob(jobName)
                .build();

        JobDetail job = getJobDetail(engine, jobName);

        try {
            LOGGER.log(Level.INFO, "Scheduling engine ''{0}'' to start at {1} and every {2} second(s)", new Object[]{engine, startTime, interval});
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("Unable to schedule engine " + engine, e);
        }
    }

    /**
     * Schedule an engine with a unix-like cron expression.
     *
     * @param engine         the engine to schedule
     * @param cronExpression the cron expression to use.
     *                       For a complete tutorial about cron expressions, please refer to
     *                       <a href="http://quartz-scheduler.org/documentation/quartz-2.1.x/tutorials/crontrigger">quartz reference documentation</a>.
     */
    public void scheduleCron(final RulesEngine engine, final String cronExpression) throws RulesEngineSchedulerException {
        checkNotNull(engine, "engine");
        checkNotNull(cronExpression, "cronExpression");

        String engineName = engine.getName();
        String jobName = JOB_NAME_PREFIX + engineName;
        String triggerName = TRIGGER_NAME_PREFIX + engineName;

        Trigger trigger = newTrigger()
                .withIdentity(triggerName)
                .withSchedule(cronSchedule(cronExpression))
                .forJob(jobName)
                .build();

        JobDetail job = getJobDetail(engine, jobName);

        try {
            LOGGER.log(Level.INFO, "Scheduling engine ''{0}'' with cron expression {1}", new Object[]{engine, cronExpression});
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("Unable to schedule engine " + engine, e);
        }
    }

    /**
     * Unschedule the given engine.
     *
     * @param engine the engine to unschedule
     * @throws RulesEngineSchedulerException thrown if an exception occurs during engine unscheduling
     */
    public void unschedule(final RulesEngine engine) throws RulesEngineSchedulerException {
        LOGGER.log(Level.INFO, "Unscheduling engine ''{0}'' ", engine);
        try {
            scheduler.unscheduleJob(TriggerKey.triggerKey(TRIGGER_NAME_PREFIX + engine.getName()));
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("Unable to unschedule engine " + engine, e);
        }
    }

    /**
     * Check if the given engine is scheduled.
     *
     * @param engine the engine to check
     * @return true if the engine is scheduled, false else
     * @throws RulesEngineSchedulerException thrown if an exception occurs while checking if the engine is scheduled
     */
    public boolean isScheduled(final RulesEngine engine) throws RulesEngineSchedulerException {
        try {
            return scheduler.checkExists(TriggerKey.triggerKey(TRIGGER_NAME_PREFIX + engine.getName()));
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("Unable to check if the engine '" + engine + "' is scheduled", e);
        }
    }

    /**
     * Start the scheduler.
     *
     * @throws RulesEngineSchedulerException thrown if the scheduler cannot be started
     */
    public void start() throws RulesEngineSchedulerException {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RulesEngineSchedulerException("An exception occurred during scheduler startup", e);
        }
    }


    /**
     * Stop the scheduler.
     * <p/>
     * <strong>Note: The scheduler cannot be re-started and no more engines can be scheduled.</strong>
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

    private JobDetail getJobDetail(RulesEngine engine, String jobName) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("engine", engine);
        return newJob(RulesEngineJob.class).withIdentity(jobName).usingJobData(jobDataMap).build();
    }

}

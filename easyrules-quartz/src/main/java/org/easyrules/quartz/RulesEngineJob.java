package org.easyrules.quartz;

import org.easyrules.api.RulesEngine;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Quartz Job implementation to launch Rules Engine instances.
 *
 * Created by Sunand on 6/8/2015.
 */
class RulesEngineJob implements Job {

    /**
     * Rules Engine instance
     */
    private RulesEngine engine;

    public RulesEngineJob(RulesEngine engine) {
        this.engine = engine;
    }

    /**
     * {@inheritDoc}
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            engine.fireRules();
        }
        catch (Exception e) {
            throw new JobExecutionException("An exception occurred during rules engine execution", e);
        }
    }
}

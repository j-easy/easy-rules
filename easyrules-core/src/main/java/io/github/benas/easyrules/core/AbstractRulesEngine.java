package io.github.benas.easyrules.core;

import io.github.benas.easyrules.api.RulesEngine;
import io.github.benas.easyrules.util.EasyRulesConstants;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract rules engine class.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public abstract class AbstractRulesEngine<R> implements RulesEngine<R> {

    protected static final Logger LOGGER = Logger.getLogger(EasyRulesConstants.LOGGER_NAME);

    /**
     * The rules set.
     */
    protected Set<R> rules;

    /**
     * Parameter to skip next applicable rules when a rule is applied.
     */
    protected boolean skipOnFirstAppliedRule;

    /**
     * Parameter to skip next rules if priority exceeds a user defined threshold.
     */
    protected int rulePriorityThreshold;

    /**
     * The JMX server instance in which rule's MBeans will be registered.
     */
    protected MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

    @Override
    public void registerRule(R rule) {
        rules.add(rule);
    }

    @Override
    public void registerJmxRule(R rule) {
        rules.add(rule);
        registerJmxMBean(rule);
    }

    @Override
    public abstract void fireRules();

    @Override
    public void clearRules() {
        rules.clear();
        LOGGER.info("Rules cleared.");
    }

    /*
    * Register a JMX MBean for a rule.
    */
    protected void registerJmxMBean(final R rule) {

        ObjectName name;
        try {
            name = new ObjectName("io.github.benas.easyrules.jmx:type=" + rule.getClass().getSimpleName() + ",name=" + rule.toString());
            if (!mBeanServer.isRegistered(name)) {
                mBeanServer.registerMBean(rule, name);
                LOGGER.log(Level.INFO, "JMX MBean registered successfully as: {0} for rule: {1}", new Object[]{name.getCanonicalName(), rule.toString()});
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to register JMX MBean for rule: " + rule.toString(), e);
        }

    }

}

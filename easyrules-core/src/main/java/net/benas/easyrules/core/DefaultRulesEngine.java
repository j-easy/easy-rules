/*
 * The MIT License
 *
 *  Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package net.benas.easyrules.core;

import net.benas.easyrules.api.JmxManagedRule;
import net.benas.easyrules.api.RulesEngine;
import net.benas.easyrules.util.EasyRulesConstants;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default {@link RulesEngine} implementation.<br/>
 * This implementation handles a set of rules with unique name. Rules are fired according to their priority (lower
 * values represent higher priorities).
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class DefaultRulesEngine implements RulesEngine {

    private final Logger logger = Logger.getLogger(EasyRulesConstants.LOGGER_NAME);

    /**
     * Rules registry.
     */
    private Set<Rule> rules;

    /**
     * Parameter to skip next applicable rules when a rule is applied.
     */
    private boolean skipOnFirstAppliedRule;

    /**
     * Parameter to skip next rules if priority exceeds a user defined threshold.
     */
    private int rulePriorityThreshold;

    /**
     * The JMX server instance in which rule MBeans will be registered.
     */
    private MBeanServer mBeanServer;

    public DefaultRulesEngine() {
        rules = new TreeSet<Rule>();
        skipOnFirstAppliedRule = false;
        rulePriorityThreshold = EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD;
        mBeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    @Override
    public void registerRule(Rule rule) {
        rules.add(rule);
    }

    @Override
    public void registerJmxManagedRule(Rule rule, boolean jmxManagedRule) {
        registerJmxManagedRule(rule, jmxManagedRule, JmxManagedRule.class);
    }

    @Override
    public void registerJmxManagedRule(Rule rule, boolean jmxManagedRule, Class clazz) {
        rules.add(rule);
        if (jmxManagedRule) {
            configureJmxMBean(rule, clazz);
        }
    }

    @Override
    public void registerRules(Set<Rule> rules) {
        this.rules.addAll(rules);
    }

    @Override
    public final void fireRules() {

        if (rules.isEmpty()) {
            logger.warning("No rules registered! Nothing to apply.");
            return;
        }

        //resort rules in case priorities were modified via JMX
        rules = new TreeSet<Rule>(rules);

        for (Rule rule : rules) {

            if (rule.getPriority() > rulePriorityThreshold) {
                logger.info("Rules priority threshold " + rulePriorityThreshold + " exceeded at " + rule.getName() + ", next applicable rules will be skipped.");
                break;
            }

            //apply rule
            if (rule.evaluateConditions()) {
                logger.info("Rule '" + rule.getName() + "' triggered.");
                try {
                    rule.performActions();
                    logger.info("Rule '" + rule.getName() + "' performed successfully.");

                    if (skipOnFirstAppliedRule) {
                        logger.info("Next rules will be skipped according to parameter skipOnFirstAppliedRule.");
                        break;
                    }

                } catch (Exception exception) {
                    logger.log(Level.SEVERE,"Rule '" + rule.getName() + "' performed with error.", exception);
                }
            }
        }
    }

    @Override
    public final void clearRules() {
        rules.clear();
        logger.info("Rules cleared.");
    }

    @Override
    public void setSkipOnFirstAppliedRule(boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
    }

    @Override
    public void setRulePriorityThreshold(int rulePriorityThreshold) {
        this.rulePriorityThreshold = rulePriorityThreshold;
    }

    /*
    * Configure a JMX MBean for a rule.
    */
    private void configureJmxMBean(Rule rule, Class clazz) {

        ObjectName name;
        try {
            name = new ObjectName("net.benas.easyrules.jmx:type=" + clazz.getSimpleName() + ",name=" + rule.getName());
            if (!mBeanServer.isRegistered(name)) {
                mBeanServer.registerMBean(rule, name);
                logger.info("JMX MBean registered successfully as: " + name.getCanonicalName() + " for rule : " + rule.getName());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to register JMX MBean for rule : " + rule.getName(), e);
        }
    }

}

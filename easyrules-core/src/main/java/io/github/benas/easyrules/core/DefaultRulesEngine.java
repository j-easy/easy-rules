/*
 * The MIT License
 *
 *  Copyright (c) 2014, Mahmoud Ben Hassine (md.benhassine@gmail.com)
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

package io.github.benas.easyrules.core;

import io.github.benas.easyrules.api.JmxManagedRule;
import io.github.benas.easyrules.api.Rule;
import io.github.benas.easyrules.api.RulesEngine;
import io.github.benas.easyrules.util.EasyRulesConstants;

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
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class DefaultRulesEngine implements RulesEngine {

    private static final Logger LOGGER = Logger.getLogger(EasyRulesConstants.LOGGER_NAME);

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
    public void registerRule(final Rule rule) {
        rules.add(rule);
    }

    @Override
    public void registerJmxManagedRule(final JmxManagedRule rule) {
        rules.add(rule);
        registerJmxMBean(rule);
    }

    @Override
    public void registerRules(final Set<Rule> rules) {
        this.rules.addAll(rules);
    }

    @Override
    public void registerJmxManagedRules(final Set<JmxManagedRule> rules) {
        for (JmxManagedRule rule : rules) {
            registerJmxManagedRule(rule);
        }
    }

    @Override
    public final void fireRules() {

        if (rules.isEmpty()) {
            LOGGER.warning("No rules registered! Nothing to apply.");
            return;
        }

        //resort rules in case priorities were modified via JMX
        rules = new TreeSet<Rule>(rules);

        for (Rule rule : rules) {

            if (rule.getPriority() > rulePriorityThreshold) {
                LOGGER.log(Level.INFO, "Rules priority threshold {0} exceeded at rule {1} (priority={2}), next applicable rules will be skipped.",
                        new Object[] {rulePriorityThreshold, rule.getName(), rule.getPriority()});
                break;
            }

            //apply rule
            if (rule.evaluateConditions()) {
                LOGGER.log(Level.INFO, "Rule {0} triggered.", new Object[]{rule.getName()});
                try {
                    rule.performActions();
                    LOGGER.log(Level.INFO, "Rule {0} performed successfully.", new Object[]{rule.getName()});

                    if (skipOnFirstAppliedRule) {
                        LOGGER.info("Next rules will be skipped according to parameter skipOnFirstAppliedRule.");
                        break;
                    }

                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, "Rule '" + rule.getName() + "' performed with error.", exception);
                }
            }
        }
    }

    @Override
    public final void clearRules() {
        rules.clear();
        LOGGER.info("Rules cleared.");
    }

    @Override
    public void setSkipOnFirstAppliedRule(final boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
    }

    @Override
    public void setRulePriorityThreshold(final int rulePriorityThreshold) {
        this.rulePriorityThreshold = rulePriorityThreshold;
    }

    /*
    * Register a JMX MBean for a rule.
    */
    private void registerJmxMBean(final Rule rule) {

        ObjectName name;
        try {
            name = new ObjectName("io.github.benas.easyrules.jmx:type=" + JmxManagedRule.class.getSimpleName() + ",name=" + rule.getName());
            if (!mBeanServer.isRegistered(name)) {
                mBeanServer.registerMBean(rule, name);
                LOGGER.log(Level.INFO, "JMX MBean registered successfully as: {0} for rule: {1}", new Object[]{name.getCanonicalName(), rule.getName()});
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to register JMX MBean for rule: " + rule.getName(), e);
        }
    }

}

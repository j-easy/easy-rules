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

import io.github.benas.easyrules.api.Rule;
import io.github.benas.easyrules.api.RulesEngine;
import io.github.benas.easyrules.util.EasyRulesConstants;

import java.util.TreeSet;
import java.util.logging.Level;

/**
 * Default {@link RulesEngine} implementation.<br/>
 *
 * This implementation handles a set of rules with unique name.
 *
 * Rules are fired according to their natural order which is priority by default.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class DefaultRulesEngine extends AbstractRulesEngine<Rule> {

    /**
     * Construct a default rules engine with default values.
     */
    public DefaultRulesEngine() {
        this(false, EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD);
    }

    /**
     * Constructs a default rules engine.
     * @param skipOnFirstAppliedRule true if the engine should skip next rule after the first applied rule
     */
    public DefaultRulesEngine(boolean skipOnFirstAppliedRule) {
        this(skipOnFirstAppliedRule, EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD);
    }

    /**
     * Constructs a default rules engine.
     * @param rulePriorityThreshold rule priority threshold
     */
    public DefaultRulesEngine(int rulePriorityThreshold) {
        this(false, rulePriorityThreshold);
    }

    /**
     * Constructs a default rules engine.
     * @param skipOnFirstAppliedRule true if the engine should skip next rule after the first applied rule
     * @param rulePriorityThreshold rule priority threshold
     */
    public DefaultRulesEngine(boolean skipOnFirstAppliedRule, int rulePriorityThreshold) {
        rules = new TreeSet<Rule>();
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        this.rulePriorityThreshold = rulePriorityThreshold;
    }

    @Override
    public void fireRules() {

        if (rules.isEmpty()) {
            LOGGER.warning("No rules registered! Nothing to apply.");
            return;
        }

        //resort rules in case priorities were modified via JMX
        rules = new TreeSet<Rule>(rules);

        for (Rule rule : rules) {

            if (rule.getPriority() > rulePriorityThreshold) {
                LOGGER.log(Level.INFO, "Rule priority threshold {0} exceeded at rule {1} (priority={2}), next applicable rules will be skipped.",
                        new Object[] {rulePriorityThreshold, rule.getName(), rule.getPriority()});
                break;
            }

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

}

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

import io.github.benas.easyrules.api.PriorityRule;
import io.github.benas.easyrules.util.EasyRulesConstants;

import java.util.TreeSet;
import java.util.logging.Level;

/**
 * Implementation of {@link io.github.benas.easyrules.api.RulesEngine} that holds a sorted set of
 * priority rules according to their priority. Rules are fired according to their natural order
 * (lower values represent higher priorities).
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class PriorityRulesEngine extends AbstractRulesEngine<PriorityRule> {

    /**
     * Parameter to skip next rules if priority exceeds a user defined threshold.
     */
    protected int rulePriorityThreshold;

    /**
     * Construct a priority rules engine with default values.
     */
    public PriorityRulesEngine() {
        this(false, EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD);
    }

    /**
     * Constructs a priority rules engine.
     * @param skipOnFirstAppliedRule true if the engine should skip next rule after the first applied rule
     */
    public PriorityRulesEngine(boolean skipOnFirstAppliedRule) {
        this(skipOnFirstAppliedRule, EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD);
    }

    /**
     * Constructs a priority rules engine.
     * @param rulePriorityThreshold rule priority threshold
     */
    public PriorityRulesEngine(int rulePriorityThreshold) {
        this(false, rulePriorityThreshold);
    }

    /**
     * Constructs a priority rules engine.
     * @param skipOnFirstAppliedRule true if the engine should skip next rule after the first applied rule
     * @param rulePriorityThreshold rule priority threshold
     */
    public PriorityRulesEngine(boolean skipOnFirstAppliedRule, int rulePriorityThreshold) {
        rules = new TreeSet<PriorityRule>();
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
        rules = new TreeSet<PriorityRule>(rules);

        for (PriorityRule priorityRule : rules) {

            if (priorityRule.getPriority() > rulePriorityThreshold) {
                LOGGER.log(Level.INFO, "Rules priority threshold {0} exceeded at rule {1} (priority={2}), next applicable rules will be skipped.",
                        new Object[] {rulePriorityThreshold, priorityRule.getName(), priorityRule.getPriority()});
                break;
            }

            if (priorityRule.evaluateConditions()) {
                LOGGER.log(Level.INFO, "Rule {0} triggered.", new Object[]{priorityRule.getName()});
                try {
                    priorityRule.performActions();
                    LOGGER.log(Level.INFO, "Rule {0} performed successfully.", new Object[]{priorityRule.getName()});
                    if (skipOnFirstAppliedRule) {
                        LOGGER.info("Next rules will be skipped according to parameter skipOnFirstAppliedRule.");
                        break;
                    }
                } catch (Exception exception) {
                    LOGGER.log(Level.SEVERE, "Rule '" + priorityRule.getName() + "' performed with error.", exception);
                }
            }

        }

    }

}

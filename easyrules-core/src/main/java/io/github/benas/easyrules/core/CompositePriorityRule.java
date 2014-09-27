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

import java.util.Set;
import java.util.TreeSet;

/**
 * Class representing a composite rule composed of a set of priority rules.<br/>
 *
 * A composite rule is triggered if <strong>ALL</strong> conditions of its composing rules are satisfied.
 * When a composite rule is applied, actions of <strong>ALL</strong> composing rules are performed in their
 * natural order.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class CompositePriorityRule extends BasicPriorityRule {

    /**
     * The set of composing priority rules.
     */
    protected Set<PriorityRule> rules;

    public CompositePriorityRule() {
        this(EasyRulesConstants.DEFAULT_RULE_NAME,
                EasyRulesConstants.DEFAULT_RULE_DESCRIPTION);
    }

    public CompositePriorityRule(final String name) {
        this(name, EasyRulesConstants.DEFAULT_RULE_DESCRIPTION);
    }

    public CompositePriorityRule(final String name, final String description) {
        super(name, description);
        rules = new TreeSet<PriorityRule>();
    }

    /**
     * A composite rule is triggered if <strong>ALL</strong> conditions of all composing rules are evaluated to true.
     * @return true if <strong>ALL</strong> conditions of composing rules are evaluated to true
     */
    @Override
    public boolean evaluateConditions() {
        if (!rules.isEmpty()) {
            for (PriorityRule rule : rules) {
                if (!rule.evaluateConditions()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * When a composite priority rule is applied, <strong>ALL</strong> actions of composing rules are performed
     * in their natural order.
     *
     * @throws Exception thrown if an exception occurs during actions performing
     */
    @Override
    public void performActions() throws Exception {
        for (PriorityRule rule : rules) {
            rule.performActions();
        }
    }

    /**
     * Add a priority rule to the composite rule.
     * @param rule the priority rule to add
     */
    public void addRule(final PriorityRule rule) {
        rules.add(rule);
    }

    /**
     * Remove a priority rule from the composite rule.
     * @param rule the priority rule to remove
     */
    public void removeRule(final PriorityRule rule) {
        rules.remove(rule);
    }

}

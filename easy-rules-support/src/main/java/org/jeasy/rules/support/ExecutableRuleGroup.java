/**
 * The MIT License
 *
 *  Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.support;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

/**
 * An executable rule group is a composite rule that has it's own action defined. When all rules
 * in the rule group are true, the actions of the rules are fired, and then the group's own.
 *
 * @author Frank Bosman (fbushman at gmail dot com)
 */
public class ExecutableRuleGroup extends CompositeRule {

    protected final Action action;

    /**
     * Create an actionable rule group.
     *
     * @param action of the executable rule group
     */
    public ExecutableRuleGroup(final Action action) {
        super();
        this.action = action;
    }

    /**
     * Create an actionable rule group.
     *
     * @param name of the executable rule group
     * @param action of the executable rule group
     */
    public ExecutableRuleGroup(final String name, final Action action) {
        super(name);
        this.action = action;
    }

    /**
     * Create an actionable rule group.
     *
     * @param name of the executable rule group
     * @param description of the executable rule group
     * @param action of the executable rule group
     */
    public ExecutableRuleGroup(final String name, final String description, final Action action) {
        super(name, description);
        this.action = action;
    }

    /**
     * Create an actionable rule group.
     *
     * @param name of the executable rule group
     * @param description of the executable rule group
     * @param priority of the executable rule group
     * @param action of the executable rule group
     */
    public ExecutableRuleGroup(final String name, final String description, final int priority, final Action action) {
        super(name, description, priority);
        this.action = action;
    }

    @Override
    public boolean evaluate(Facts facts) {
        // No rules, group is false
        if (rules.isEmpty()) {
            return false;
        }
        for (Rule rule : rules) {
            // Any rule is false, group is false
            if (!rule.evaluate(facts)) {
                return false;
            }
        }
        // Only return true when all rules where true
        return true;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        for (Rule rule : rules) {
            rule.execute(facts);
        }
        this.action.execute(facts);
    }
}

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

/**
 * Priority rule implementation that provides common methods.<br/>
 *
 * This class adds rule priority to fire rules according to their priorities.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class BasicPriorityRule extends BasicRule implements PriorityRule, Comparable<PriorityRule> {

    /**
     * Rule priority.
     */
    protected int priority;

    public BasicPriorityRule() {
        super();
        this.priority = EasyRulesConstants.DEFAULT_RULE_PRIORITY;
    }

    public BasicPriorityRule(final String name) {
        super(name);
        this.priority = EasyRulesConstants.DEFAULT_RULE_PRIORITY;
    }

    public BasicPriorityRule(final String name, final String description) {
        this(name, description, EasyRulesConstants.DEFAULT_RULE_PRIORITY);
    }

    public BasicPriorityRule(final String name, final String description, final int priority) {
        super(name, description);
        this.priority = priority;
    }

    @Override
    public int compareTo(final PriorityRule rule) {
        if (priority < rule.getPriority()) {
            return -1;
        } else if (priority == rule.getPriority()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

}

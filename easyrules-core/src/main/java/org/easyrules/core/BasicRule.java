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

package org.easyrules.core;

import org.easyrules.api.Rule;
import org.easyrules.util.EasyRulesConstants;

/**
 * Basic rule implementation class that provides common methods.
 *
 * You can extend this class and override {@link BasicRule#evaluateConditions()} and {@link BasicRule#performActions()}
 * to provide rule conditions and actions logic.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class BasicRule implements Rule, Comparable<Rule> {

    /**
     * Rule name.
     */
    protected String name;

    /**
     * Rule description.
     */
    protected String description;

    /**
     * Rule priority.
     */
    private int priority;

    public BasicRule() {
        this(EasyRulesConstants.DEFAULT_RULE_NAME,
                EasyRulesConstants.DEFAULT_RULE_DESCRIPTION,
                EasyRulesConstants.DEFAULT_RULE_PRIORITY);
    }

    public BasicRule(final String name) {
        this(name, EasyRulesConstants.DEFAULT_RULE_DESCRIPTION, EasyRulesConstants.DEFAULT_RULE_PRIORITY);
    }

    public BasicRule(final String name, final String description) {
        this(name, description, EasyRulesConstants.DEFAULT_RULE_PRIORITY);
    }

    public BasicRule(final String name, final String description, final int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    /**
     * {@inheritDoc}
     */
    public boolean evaluateConditions() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void performActions() throws Exception {
        //no op
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /*
     * Rules are unique according to their names within a rules engine registry.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        return name.equals(rule.getName());

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(final Rule rule) {
        if (priority < rule.getPriority()) {
            return -1;
        } else if (priority == rule.getPriority()) {
            return 0;
        } else {
            return 1;
        }
    }

}

/*
 * The MIT License
 *
 *  Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
import org.easyrules.util.Utils;

import javax.management.MXBean;

/**
 * Basic rule implementation class that provides common methods.
 *
 * You can extend this class and override {@link BasicRule#evaluate()} and {@link BasicRule#execute()}
 * to provide rule conditions and actions logic.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@MXBean
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
    protected int priority;

    public BasicRule() {
        this(Utils.DEFAULT_RULE_NAME, Utils.DEFAULT_RULE_DESCRIPTION, Utils.DEFAULT_RULE_PRIORITY);
    }

    public BasicRule(final String name) {
        this(name, Utils.DEFAULT_RULE_DESCRIPTION, Utils.DEFAULT_RULE_PRIORITY);
    }

    public BasicRule(final String name, final String description) {
        this(name, description, Utils.DEFAULT_RULE_PRIORITY);
    }

    public BasicRule(final String name, final String description, final int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    /**
     * {@inheritDoc}
     */
    public boolean evaluate() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void execute() throws Exception {
        //no op
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(final int priority) {
        this.priority = priority;
    }

    /*
     * Rules are unique according to their names within a rules engine registry.
     */

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicRule basicRule = (BasicRule) o;

        if (priority != basicRule.priority) return false;
        if (!name.equals(basicRule.name)) return false;
        return !(description != null ? !description.equals(basicRule.description) : basicRule.description != null);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + priority;
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(final Rule rule) {
        if (priority < rule.getPriority()) {
            return -1;
        } else if (priority > rule.getPriority()) {
            return 1;
        } else {
            return name.compareTo(rule.getName());
        }
    }

}

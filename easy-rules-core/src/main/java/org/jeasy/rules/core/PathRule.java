/**
 * The MIT License
 *
 *  Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.core;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a path rule composed of the primary rule that decides if we shall evaluate a set of rules.
 *
 * A path rule will triggered all it's rules if the condition of its primary rule is satisfied.
 *
 * @author Dag Framstad (dagframstad@gmail.com)
 */
public class PathRule extends CompositeRule {

    private Rule primaryRule;
    private Set<Rule> successfulEvaluations;

    /**
     * Create a new {@link PathRule}.
     */
    public PathRule(Rule primaryRule) {
        this(DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_PRIORITY, primaryRule);
    }

    /**
     * Create a new {@link PathRule}.
     *
     * @param name rule name
     */
    public PathRule(final String name, Rule primaryRule) {
        this(name, DEFAULT_DESCRIPTION, DEFAULT_PRIORITY, primaryRule);
    }

    /**
     * Create a new {@link PathRule}.
     *
     * @param name rule name
     * @param description rule description
     */
    public PathRule(final String name, final String description, Rule primaryRule) {
        this(name, description, DEFAULT_PRIORITY, primaryRule);
    }

    /**
     * Create a new {@link PathRule}.
     *
     * @param name rule name
     * @param description rule description
     * @param priority rule priority
     */
    public PathRule(final String name, final String description, final int priority, Rule primaryRule) {
        super(name, description, priority);
        this.primaryRule = primaryRule;
    }

    /**
     * A path rule will trigger all it's rules if the path rule's condition is true.
     * @param facts The facts.
     * @return true if the path rules condition is true.
     */
    @Override
    public boolean evaluate(Facts facts) {
        successfulEvaluations = new HashSet<>();
        if (primaryRule.evaluate(facts)) {
            for (Rule rule : rules) {
                if (rule.evaluate(facts)) {
                    successfulEvaluations.add(rule);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * When a path rule is applied, all rules that evaluated to true are performed
     * in their natural order, but with the path rule first.
     *
     * @param facts The facts.
     *
     * @throws Exception thrown if an exception occurs during actions performing
     */
    @Override
    public void execute(Facts facts) throws Exception {
        primaryRule.execute(facts);
        for (Rule rule : successfulEvaluations) {
            rule.execute(facts);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        PathRule pathRule = (PathRule) o;
        return primaryRule.equals(pathRule.primaryRule) &&
                successfulEvaluations.equals(pathRule.successfulEvaluations);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (primaryRule == null ? 0 : primaryRule.hashCode());
        result = 31 * result + (successfulEvaluations == null ? 0 : successfulEvaluations.hashCode());
        return result;
    }

}

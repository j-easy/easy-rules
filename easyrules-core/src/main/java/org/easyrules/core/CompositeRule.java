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
package org.easyrules.core;

import org.easyrules.api.Facts;
import org.easyrules.api.Rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.easyrules.core.RuleProxy.asRule;

/**
 * Class representing a composite rule composed of a set of rules.
 *
 * A composite rule is triggered if <strong>ALL</strong> conditions of its composing rules are satisfied.
 * When a composite rule is applied, actions of <strong>ALL</strong> composing rules are performed.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class CompositeRule extends BasicRule {

    /**
     * The set of composing rules.
     */
    protected Set<Rule> rules;

    protected Map<Object, Rule> proxyRules;

    /**
     * Create a new {@link CompositeRule}.
     */
    public CompositeRule() {
        this(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
    }

    /**
     * Create a new {@link CompositeRule}.
     *
     * @param name rule name
     */
    public CompositeRule(final String name) {
        this(name, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
    }

    /**
     * Create a new {@link CompositeRule}.
     *
     * @param name rule name
     * @param description rule description
     */
    public CompositeRule(final String name, final String description) {
        this(name, description, Rule.DEFAULT_PRIORITY);
    }

    /**
     * Create a new {@link CompositeRule}.
     *
     * @param name rule name
     * @param description rule description
     * @param priority rule priority
     */
    public CompositeRule(final String name, final String description, final int priority) {
        super(name, description, priority);
        rules = new TreeSet<>();
        proxyRules = new HashMap<>();
    }

    /**
     * A composite rule is triggered if <strong>ALL</strong> conditions of all composing rules are evaluated to true.
     * @return true if <strong>ALL</strong> conditions of composing rules are evaluated to true
     */
    @Override
    public boolean evaluate(Facts facts) {
        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if (!rule.evaluate(facts)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * When a composite rule is applied, <strong>ALL</strong> actions of composing rules are performed
     * in their natural order.
     *
     * @throws Exception thrown if an exception occurs during actions performing
     */
    @Override
    public void execute(Facts facts) throws Exception {
        for (Rule rule : rules) {
            rule.execute(facts);
        }
    }

    /**
     * Add a rule to the composite rule.
     * @param rule the rule to add
     */
    public void addRule(final Object rule) {
        Rule proxy = asRule(rule);
        rules.add(proxy);
        proxyRules.put(rule, proxy);
    }

    /**
     * Remove a rule from the composite rule.
     * @param rule the rule to remove
     */
    public void removeRule(final Object rule) {
        Rule proxy = proxyRules.get(rule);
        if (proxy != null) {
            rules.remove(proxy);
        }
    }

}

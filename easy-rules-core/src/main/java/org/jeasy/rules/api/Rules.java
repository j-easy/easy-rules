/*
 * The MIT License
 *
 *  Copyright (c) 2022, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.api;

import org.jeasy.rules.core.RuleProxy;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class encapsulates a set of rules and represents a rules namespace.
 * Rules must have a unique name within a rules namespace.
 * 
 * Rules will be compared to each other based on {@link Rule#compareTo(Object)}
 * method, so {@link Rule}'s implementations are expected to correctly implement
 * {@code compareTo} to ensure unique rule names within a single namespace.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class Rules implements Iterable<Rule> {

    private Set<Rule> rules = new TreeSet<>();

    /**
     * Create a new {@link Rules} object.
     *
     * @param rules to register
     */
    public Rules(Set<Rule> rules) {
        this.rules = new TreeSet<>(rules);
    }

    /**
     * Create a new {@link Rules} object.
     *
     * @param rules to register
     */
    public Rules(Rule... rules) {
        Collections.addAll(this.rules, rules);
    }

    /**
     * Create a new {@link Rules} object.
     *
     * @param rules to register
     */
    public Rules(Object... rules) {
        this.register(rules);
    }

    /**
     * Register one or more new rules.
     *
     * @param rules to register, must not be null
     */
    public void register(Object... rules) {
        Objects.requireNonNull(rules);
        for (Object rule : rules) {
            Objects.requireNonNull(rule);
            this.rules.add(RuleProxy.asRule(rule));
        }
    }

    /**
     * Unregister one or more rules.
     *
     * @param rules to unregister, must not be null
     */
    public void unregister(Object... rules) {
        Objects.requireNonNull(rules);
        for (Object rule : rules) {
            Objects.requireNonNull(rule);
            this.rules.remove(RuleProxy.asRule(rule));
        }
    }

    /**
     * Unregister a rule by name.
     *
     * @param ruleName name of the rule to unregister, must not be null
     */
    public void unregister(final String ruleName) {
        Objects.requireNonNull(ruleName);
        Rule rule = findRuleByName(ruleName);
        if (rule != null) {
            unregister(rule);
        }
    }

    /**
     * Check if the rule set is empty.
     *
     * @return true if the rule set is empty, false otherwise
     */
    public boolean isEmpty() {
        return rules.isEmpty();
    }

    /**
     * Clear rules.
     */
    public void clear() {
        rules.clear();
    }

    /**
     * Return how many rules are currently registered.
     *
     * @return the number of rules currently registered
     */
    public int size() {
        return rules.size();
    }

    /**
     * Return an iterator on the rules set. It is not intended to remove rules
     * using this iterator.
     * @return an iterator on the rules set
     */
    @Override
    public Iterator<Rule> iterator() {
        return rules.iterator();
    }

    private Rule findRuleByName(String ruleName) {
        return rules.stream()
                .filter(rule -> rule.getName().equalsIgnoreCase(ruleName))
                .findFirst()
                .orElse(null);
    }
}

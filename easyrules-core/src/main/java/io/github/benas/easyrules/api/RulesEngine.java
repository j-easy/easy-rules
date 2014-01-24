/*
 * The MIT License
 *
 *  Copyright (c) 2014, benas (md.benhassine@gmail.com)
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

package io.github.benas.easyrules.api;

import java.util.Set;

/**
 * Rules engine interface.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public interface RulesEngine {

    /**
     * Register a rule in the rules engine registry.
     * @param rule the rule to register
     */
    void registerRule(Rule rule);

    /**
     * Register a JMX managed rule in the rules engine registry. By default, the JMX MBean type is {@link JmxManagedRule}
     * @param rule the rule to register
     */
    void registerJmxManagedRule(JmxManagedRule rule);

    /**
     * Register a set of rules in the rules engine registry.
     * @param rules rules to register
     */
    void registerRules(Set<Rule> rules);

    /**
     * Register a set of JMX managed rules in the rules engine registry.
     * @param rules JMX managed rules to register
     */
    void registerJmxManagedRules(Set<JmxManagedRule> rules);

    /**
     * Fire all registered rules.
     */
    void fireRules();

    /**
     * Clear rules engine registry.
     */
    void clearRules();

    /**
     * Specify if next rules should be skipped after the first applied rule.
     * @param skipOnFirstAppliedRule true if next rules should be skipped after the first applied rule, false else.
     */
    void setSkipOnFirstAppliedRule(boolean skipOnFirstAppliedRule);

    /**
     * Set the maximum rule priority over which rules should be skipped.
     * @param rulePriorityThreshold rule priority threshold
     */
    void setRulePriorityThreshold(int rulePriorityThreshold);

}

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
package org.easyrules.api;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.easyrules.core.RulesEngineParameters;

/**
 * Rules engine interface.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public interface RulesEngine {

    /**
     * Default engine name.
     */
    String DEFAULT_NAME = "engine";

    /**
     * Default rule priority threshold.
     */
    int DEFAULT_RULE_PRIORITY_THRESHOLD = Integer.MAX_VALUE;

    /**
     * Return the rules engine parameters.
     *
     * @return The rules engine parameters
     */
    RulesEngineParameters getParameters();

    /**
     * Register a rule in the rules engine registry.
     *
     * @param rule the rule to register
     */
    void registerRule(Object rule);

    /**
     * Unregister a rule from the rules engine registry.
     *
     * @param rule the rule to unregister
     */
    void unregisterRule(Object rule);

    /**
     * Return the set of registered rules.
     *
     * @return the set of registered rules
     */
    Set<Rule> getRules();

    /**
     * Return the list of registered rule listeners.
     *
     * @return the list of registered rule listeners
     */
    List<RuleListener> getRuleListeners();

    /**
     * Fire all registered rules.
     */
    void fireRules();

    /**
     * Check rules without firing them.
     * @return a map with the result of evaluation of each rule
     */
    Map<Rule, Boolean> checkRules();

    /**
     * Clear rules engine registry.
     */
    void clearRules();

    void unregisterRuleByName(String r1);
}

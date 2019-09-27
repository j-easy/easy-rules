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
package org.jeasy.rules.api;

import org.jeasy.rules.core.RulesEngineParameters;

import java.util.List;
import java.util.Map;

/**
 * Rules engine interface.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public interface RulesEngine {

    /**
     * Return the rules engine parameters.
     *
     * @return The rules engine parameters
     */
    RulesEngineParameters getParameters();

    /**
     * Return the list of registered rule listeners.
     *
     * @return the list of registered rule listeners
     */
    List<RuleListener> getRuleListeners();

    /**
     * Return the list of registered rules engine listeners.
     *
     * @return the list of registered rules engine listeners
     */
    List<RulesEngineListener> getRulesEngineListeners();

    /**
     * Fire all registered rules on given facts.
     */
    void fire(Rules rules, Facts facts);

    /**
     * Check rules without firing them.
     * @return a map with the result of evaluation of each rule
     */
    Map<Rule, Boolean> check(Rules rules, Facts facts);

    /**
     * Register rule listener
     *
     * @param ruleListener of type RuleListener
     */
    void registerRuleListener(RuleListener ruleListener);

    /**
     * Register multiple rule listeners
     *
     * @param ruleListeners of type List<RuleListener>
     */
    void registerRuleListener(List<RuleListener> ruleListeners);

    /**
     * Register rules engine listener
     *
     * @param rulesEngineListener of type RulesEngineListener
     */
    void registerRulesEngineListener(RulesEngineListener rulesEngineListener);

    /**
     * Register multiple rules engine listeners
     *
     * @param rulesEngineListeners of type List<RulesEngineListener>
     */
    void registerRulesEngineListener(List<RulesEngineListener> rulesEngineListeners);
}

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
package org.jeasy.rules.core;

import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for {@link RulesEngine} implementations.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
abstract class AbstractRuleEngine implements RulesEngine {

    RulesEngineParameters     parameters;
    List<RuleListener>        ruleListeners;
    List<RulesEngineListener> rulesEngineListeners;

    AbstractRuleEngine() {
        this(new RulesEngineParameters());
    }

    AbstractRuleEngine(final RulesEngineParameters parameters) {
        this.parameters = parameters;
        this.ruleListeners = new ArrayList<>();
        this.ruleListeners.add(new DefaultRuleListener());
        this.rulesEngineListeners = new ArrayList<>();
        this.rulesEngineListeners.add(new DefaultRulesEngineListener(parameters));
    }

    @Override
    public RulesEngineParameters getParameters() {
        return new RulesEngineParameters(parameters.isSkipOnFirstAppliedRule(), parameters.isSkipOnFirstFailedRule(), parameters.isSkipOnFirstNonTriggeredRule(),
            parameters.getPriorityThreshold());
    }

    @Override
    public List<RuleListener> getRuleListeners() {
        return Collections.unmodifiableList(ruleListeners);
    }

    @Override
    public List<RulesEngineListener> getRulesEngineListeners() {
        return Collections.unmodifiableList(rulesEngineListeners);
    }

    /**
     * Register rule listener
     *
     * @param ruleListener of type RuleListener
     */
    @Override
    public void registerRuleListener(RuleListener ruleListener) {
        ruleListeners.add(ruleListener);
    }

    /**
     * Register multiple rule listeners
     *
     * @param ruleListeners of type List<RuleListener>
     */
    @Override
    public void registerRuleListener(List<RuleListener> ruleListeners) {
        this.ruleListeners.addAll(ruleListeners);
    }

    /**
     * Register rules engine listener
     *
     * @param rulesEngineListener of type RulesEngineListener
     */
    @Override
    public void registerRulesEngineListener(RulesEngineListener rulesEngineListener) {
        rulesEngineListeners.add(rulesEngineListener);
    }

    /**
     * Register multiple rules engine listeners
     *
     * @param rulesEngineListeners of type List<RulesEngineListener>
     */
    @Override
    public void registerRulesEngineListener(List<RulesEngineListener> rulesEngineListeners) {
        this.rulesEngineListeners.addAll(rulesEngineListeners);
    }
}

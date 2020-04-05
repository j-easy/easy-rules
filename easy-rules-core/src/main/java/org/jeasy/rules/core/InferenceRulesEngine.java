/*
 * The MIT License
 *
 *  Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.jeasy.rules.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Inference {@link RulesEngine} implementation.
 *
 * Rules are selected based on given facts and fired according to their natural order which is priority by default.
 *
 * The engine continuously selects and fires rules until no more rules are applicable.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public final class InferenceRulesEngine extends AbstractRulesEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(InferenceRulesEngine.class);

    private DefaultRulesEngine delegate;

    /**
     * Create a new inference rules engine with default parameters.
     */
    public InferenceRulesEngine() {
        this(new RulesEngineParameters());
    }

    /**
     * Create a new inference rules engine.
     *
     * @param parameters of the engine
     */
    public InferenceRulesEngine(RulesEngineParameters parameters) {
        super(parameters);
        delegate = new DefaultRulesEngine(parameters);
    }

    @Override
    public void fire(Rules rules, Facts facts) {
        Set<Rule> selectedRules;
        do {
            LOGGER.debug("Selecting candidate rules based on the following facts: {}", facts);
            selectedRules = selectCandidates(rules, facts);
            if(!selectedRules.isEmpty()) {
                delegate.fire(new Rules(selectedRules), facts);
            } else {
                LOGGER.debug("No candidate rules found for facts: {}", facts);
            }
        } while (!selectedRules.isEmpty());
    }

    private Set<Rule> selectCandidates(Rules rules, Facts facts) {
        Set<Rule> candidates = new TreeSet<>();
        for (Rule rule : rules) {
            if (rule.evaluate(facts)) {
                candidates.add(rule);
            }
        }
        return candidates;
    }

    @Override
    public Map<Rule, Boolean> check(Rules rules, Facts facts) {
        return delegate.check(rules, facts);
    }

    /**
     * Register a rule listener.
     * @param ruleListener to register
     */
    public void registerRuleListener(RuleListener ruleListener) {
        super.registerRuleListener(ruleListener);
        delegate.registerRuleListener(ruleListener);
    }

    /**
     * Register a list of rule listener.
     * @param ruleListeners to register
     */
    public void registerRuleListeners(List<RuleListener> ruleListeners) {
        super.registerRuleListeners(ruleListeners);
        delegate.registerRuleListeners(ruleListeners);
    }

    /**
     * Register a rules engine listener.
     * @param rulesEngineListener to register
     */
    public void registerRulesEngineListener(RulesEngineListener rulesEngineListener) {
        super.registerRulesEngineListener(rulesEngineListener);
        delegate.registerRulesEngineListener(rulesEngineListener);
    }

    /**
     * Register a list of rules engine listener.
     * @param rulesEngineListeners to register
     */
    public void registerRulesEngineListeners(List<RulesEngineListener> rulesEngineListeners) {
        super.registerRulesEngineListeners(rulesEngineListeners);
        delegate.registerRulesEngineListeners(rulesEngineListeners);
    }
}

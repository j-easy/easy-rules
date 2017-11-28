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
 * The engine continuously select and fire rules until no more rules are applicable.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public final class InferenceRulesEngine implements RulesEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(InferenceRulesEngine.class);

    private RulesEngineParameters parameters;
    private List<RuleListener> ruleListeners;
    private DefaultRulesEngine delegate;

    public InferenceRulesEngine() {
        this(new RulesEngineParameters());
    }

    public InferenceRulesEngine(RulesEngineParameters parameters) {
        this(parameters, new ArrayList<RuleListener>());
    }

    public InferenceRulesEngine(RulesEngineParameters parameters, List<RuleListener> ruleListeners) {
        this.parameters = parameters;
        this.ruleListeners = ruleListeners;
        delegate = new DefaultRulesEngine(parameters, ruleListeners);
    }

    @Override
    public RulesEngineParameters getParameters() {
        return parameters;
    }

    @Override
    public List<RuleListener> getRuleListeners() {
        return ruleListeners;
    }

    @Override
    public void fire(Rules rules, Facts facts) {
        Set<Rule> selectedRules;
        do {
            LOGGER.info("Selecting candidate rules based on the following {}", facts);
            selectedRules = selectCandidates(rules, facts);
            delegate.apply(new Rules(selectedRules), facts);
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
}

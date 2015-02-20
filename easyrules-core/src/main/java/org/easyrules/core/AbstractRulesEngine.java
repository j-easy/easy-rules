package org.easyrules.core;

import java.util.Set;
import java.util.logging.Logger;

import org.easyrules.api.RulesEngine;
import org.easyrules.util.EasyRulesConstants;

/**
 * Abstract rules engine class.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public abstract class AbstractRulesEngine<R> implements RulesEngine<R> {

    private static final Logger LOGGER = Logger.getLogger(EasyRulesConstants.LOGGER_NAME);

    /**
     * The rules set.
     */
    protected Set<R> rules;

    /**
     * Parameter to skip next applicable rules when a rule is applied.
     */
    protected boolean skipOnFirstAppliedRule;

    /**
     * Parameter to skip next rules if priority exceeds a user defined threshold.
     */
    protected int rulePriorityThreshold;

    @Override
    public void registerRule(R rule) {
        rules.add(rule);
    }

    @Override
    public void unregisterRule(R rule) {
        rules.remove(rule);
    }

    @Override
    public abstract void fireRules();

    @Override
    public void clearRules() {
        rules.clear();
        LOGGER.info("Rules cleared.");
    }

}

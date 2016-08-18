package org.easyrules.api;

/**
 * A listener for rules execution events.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public interface RuleListener {

    /**
     * Triggered before the evaluation of a rule.
     *
     * @param rule being evaluated
     * @return true if the rule should be evaluated, false otherwise
     */
    boolean beforeEvaluate(Rule rule);

    /**
     * Triggered before the execution of a rule.
     *
     * @param rule the current rule
     */
    void beforeExecute(Rule rule);

    /**
     * Triggered after a rule has been executed successfully.
     *
     * @param rule the current rule
     */
    void onSuccess(Rule rule);

    /**
     * Triggered after a rule has failed.
     *
     * @param rule      the current rule
     * @param exception the exception thrown when attempting to execute the rule
     */
    void onFailure(Rule rule, Exception exception);

}

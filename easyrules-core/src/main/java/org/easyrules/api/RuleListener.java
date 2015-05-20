package org.easyrules.api;

/**
 * A listener on rules events.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public interface RuleListener {

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

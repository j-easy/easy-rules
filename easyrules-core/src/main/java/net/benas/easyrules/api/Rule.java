package net.benas.easyrules.api;

/**
 * Abstraction for a rule that can be fired by the rules engine.<br/>
 *
 * Rules are registered in the rules engine registry and must have a <strong>unique</strong> name.<br/>
 *
 * @author benas (md.benhassine@gmail.com)
 */
public interface Rule {

    /**
     * Getter for rule name.
     * @return the rule name
     */
    String getName();

    /**
     * Getter for rule description.
     * @return rule description
     */
    String getDescription();

    /**
     * Getter for rule priority.
     * @return rule priority
     */
    int getPriority();

    /**
     * Setter for rule priority.
     * @param priority the priority to set
     */
    void setPriority(int priority);

    /**
     * Setter for rule description.
     * @param description new rule description
     */
    void setDescription(String description);

    /**
     * Rule conditions abstraction : this method encapsulates the rule's conditions.
     * @return true if the rule should be applied, false else
     */
    boolean evaluateConditions();

    /**
     * Rule actions abstraction : this method encapsulates the rule's actions.
     * @throws Exception thrown if an exception occurs during actions performing
     */
    void performActions() throws Exception;

}

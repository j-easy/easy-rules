package net.benas.easyrules.api;

import javax.management.MXBean;

/**
 * Interface to make rules manageable via JMX.<br/>
 * This interface can be extended with additional methods to add more specific rule properties.
 *
 * @author benas (md.benhassine@gmail.com)
 */

@MXBean
public interface JmxManagedRule {

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

}

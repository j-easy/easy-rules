package net.benas.easyrules.samples.order;

import net.benas.easyrules.api.JmxManagedRule;

import javax.management.MXBean;

/**
 * Interface to make suspect order rule manageable via JMX.<br/>
 * Suspect order threshold should be changed at runtime.
 *
 * @author benas (md.benhassine@gmail.com)
 */

@MXBean
public interface SuspectOrderJmxManagedRule extends JmxManagedRule {

    /**
     * Get the current suspect order amount threshold
     * @return current suspect order amount threshold
     */
    float getSuspectOrderAmountThreshold();

    /**
     * Set the suspect order amount threshold
     * @param suspectOrderAmountThreshold the new suspect order amount threshold
     */
    void setSuspectOrderAmountThreshold(float suspectOrderAmountThreshold);

}

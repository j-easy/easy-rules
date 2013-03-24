package net.benas.easyrules.api;

import javax.management.MXBean;

/**
 * Interface to make rules manageable via JMX.<br/>
 * This interface can be extended with additional methods to add more specific rule properties.
 *
 * @author benas (md.benhassine@gmail.com)
 */

@MXBean
public interface JmxManagedRule extends Rule {

}

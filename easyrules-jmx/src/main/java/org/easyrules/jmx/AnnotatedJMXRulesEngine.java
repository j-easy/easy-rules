package org.easyrules.jmx;

import org.easyrules.core.AnnotatedRulesEngine;
import org.easyrules.jmx.api.JMXRule;
import org.easyrules.jmx.api.JMXRulesEngine;
import org.easyrules.jmx.util.MBeanManager;

/**
 * @author drem
 */
public class AnnotatedJMXRulesEngine extends AnnotatedRulesEngine implements
		JMXRulesEngine<Object> {

	/**
     * The MBean manager in which rule's MBeans will be registered.
     */
	private MBeanManager beanManager = new MBeanManager();
    
	@Override
	public void registerJMXRule(Object rule) {
		registerRule(rule);
		rules.add(rule);
		beanManager.registerJmxMBean((JMXRule)rule);
	}

	@Override
	public void unregisterJMXRule(Object rule) {
		unregisterRule(rule);
		rules.remove(rule);
		beanManager.unregisterJmxMBean((JMXRule)rule);
	}


}

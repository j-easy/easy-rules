package org.easyrules.jmx.util;

import org.easyrules.jmx.api.JMXRule;
import org.easyrules.util.EasyRulesConstants;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles keeping track of MBeans and helps reduce Boilerplate code.
 * 
 * @author Drem Darios (drem.darios@gmail.com)
 */
public class MBeanManager {

	private static final Logger LOGGER = Logger
			.getLogger(EasyRulesConstants.LOGGER_NAME);

	/**
	 * The JMX server instance in which rule's MBeans will be registered.
	 */
	private MBeanServer mBeanServer = ManagementFactory
			.getPlatformMBeanServer();

	/*
	 * Unregister the JMX MBean of a rule.
	 */
	public void unregisterJmxMBean(final JMXRule rule) {

		ObjectName name;
		try {
			name = getObjectName(rule);
			if (mBeanServer.isRegistered(name)) {
				mBeanServer.unregisterMBean(name);
				LOGGER.log(Level.INFO, "JMX MBean unregistered successfully for rule: \"{0}\"", rule);
			}
		} catch (Exception e) {
			LOGGER.log(
					Level.SEVERE, "Unable to unregister JMX MBean for rule: \"" + rule + "\"", e);
		}
	}

	/*
	 * Register a JMX MBean for a rule.
	 */
	public void registerJmxMBean(final JMXRule rule) {

		ObjectName name;
		try {
			name = getObjectName(rule);
			if (!mBeanServer.isRegistered(name)) {
				mBeanServer.registerMBean(rule, name);
				LOGGER.log(
						Level.INFO,
						"JMX MBean registered successfully as: \"{0}\" for rule: \"{1}\"",
						new Object[] { name.getCanonicalName(), rule.toString() });
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to register JMX MBean for rule: \"" + rule + "\"", e);
		}

	}

	/*
	 * Utility method to get rule's JMX Object name
	 */
	private ObjectName getObjectName(JMXRule rule)
			throws MalformedObjectNameException {
		return new ObjectName("org.easyrules.core.jmx:type="
				+ rule.getClass().getSimpleName() + ",name=" + rule);
	}
}

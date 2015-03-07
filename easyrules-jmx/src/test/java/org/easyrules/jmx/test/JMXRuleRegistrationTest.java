package org.easyrules.jmx.test;

import org.easyrules.jmx.BasicJMXRule;
import org.easyrules.jmx.DefaultJMXRulesEngine;
import org.easyrules.jmx.api.JMXRule;
import org.easyrules.jmx.api.JMXRulesEngine;
import org.junit.Before;
import org.junit.Test;

import javax.management.*;
import java.lang.management.ManagementFactory;

import static org.junit.Assert.*;

/**
 * Test class for JMX managed rule registration.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class JMXRuleRegistrationTest {

    private JMXRule rule;

    private JMXRulesEngine<JMXRule> rulesEngine;

    @Before
    public void setup(){

        rule = new BasicJMXRule();
        rulesEngine = new DefaultJMXRulesEngine();
    }

    @Test
    public void testJmxRuleRegistration() throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {

        rulesEngine.registerJMXRule(rule);

        //assert that the rule has been successfully registered within the JMX registry
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("org.easyrules.core.jmx:type=" + rule.getClass().getSimpleName() + ",name=" + rule.getName());
        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(name);
        assertNotNull(mBeanInfo);
        assertTrue(mBeanServer.isRegistered(name));

    }

    @Test
    public void testJmxRuleUnregistration() throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {

        rulesEngine.unregisterJMXRule(rule);

        //assert that the rule has been successfully unregistered form the JMX registry
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("org.easyrules.core.jmx:type=" + rule.getClass().getSimpleName() + ",name=" + rule.getName());
        assertFalse(mBeanServer.isRegistered(name));

    }

}

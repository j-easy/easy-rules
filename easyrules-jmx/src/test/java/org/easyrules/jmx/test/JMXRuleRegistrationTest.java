package org.easyrules.jmx.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.management.ManagementFactory;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.easyrules.jmx.BasicJMXRule;
import org.easyrules.jmx.DefaultJMXRulesEngine;
import org.easyrules.jmx.api.JMXRule;
import org.easyrules.jmx.api.JMXRulesEngine;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for JMX managed rule registration.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
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

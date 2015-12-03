package org.easyrules.core;

import org.easyrules.api.JmxRule;
import org.easyrules.api.JmxRulesEngine;
import org.easyrules.api.Rule;
import org.junit.Before;
import org.junit.Test;

import javax.management.*;
import java.lang.management.ManagementFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easyrules.core.JmxRulesEngineBuilder.aNewJmxRulesEngine;

/**
 * Test class for JMX managed rule registration.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class JmxRuleRegistrationTest {

    private JmxRule rule;

    private JmxRulesEngine rulesEngine;

    @Before
    public void setup() {

        rule = new BasicJmxRule();
        rulesEngine = aNewJmxRulesEngine().build();
    }

    @Test
    public void testJmxRuleRegistration() throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {

        rulesEngine.registerJmxRule(rule);

        //assert that the rule has been successfully registered within the JMX registry
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("org.easyrules.core.jmx:type=" + rule.getClass().getSimpleName() + ",name=" + rule.getName());
        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(name);
        assertThat(mBeanInfo).isNotNull();
        assertThat(mBeanServer.isRegistered(name)).isTrue();

    }

    @Test
    public void testJmxRuleUnregistration() throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {

        rulesEngine.unregisterJmxRule(rule);

        //assert that the rule has been successfully unregistered form the JMX registry
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("org.easyrules.core.jmx:type=" + rule.getClass().getSimpleName() + ",name=" + rule.getName());
        assertThat(mBeanServer.isRegistered(name)).isFalse();

    }

}

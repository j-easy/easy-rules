package io.github.benas.easyrules.core.test;

import io.github.benas.easyrules.api.Rule;
import io.github.benas.easyrules.api.RulesEngine;
import io.github.benas.easyrules.core.BasicRule;
import io.github.benas.easyrules.core.DefaultRulesEngine;
import org.junit.Before;
import org.junit.Test;

import javax.management.*;

import java.lang.management.ManagementFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Test class for JMX managed rule registration.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class JmxRuleRegistrationTest {

    private BasicRule rule;

    private RulesEngine rulesEngine;

    @Before
    public void setup(){

        rule = new BasicRule("rule","description",1);

        rulesEngine = new DefaultRulesEngine();
    }

    @Test
    public void testRulePriorityThreshold() throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {

        rulesEngine.registerRule(rule);

        //assert that the rule has been successfully registered within JMX registry
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("io.github.benas.easyrules.jmx:type=" + Rule.class.getSimpleName() + ",name=" + rule.getName());
        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(name);
        assertNotNull(mBeanInfo);

    }

}

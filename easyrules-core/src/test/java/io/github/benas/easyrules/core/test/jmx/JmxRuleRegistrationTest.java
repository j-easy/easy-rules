package io.github.benas.easyrules.core.test.jmx;

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

    private Rule rule;

    private RulesEngine<Rule> rulesEngine;

    @Before
    public void setup(){

        rule = new BasicRule();

        rulesEngine = new DefaultRulesEngine();
    }

    @Test
    public void testRulePriorityThreshold() throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {

        rulesEngine.registerJmxRule(rule);

        //assert that the rule has been successfully registered within JMX registry
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("io.github.benas.easyrules.jmx:type=" + rule.getClass().getSimpleName() + ",name=" + rule.getName());
        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(name);
        assertNotNull(mBeanInfo);

    }

}

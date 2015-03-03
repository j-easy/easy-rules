package org.easyrules.core.test;

import org.easyrules.core.test.annotation.EasyRulesAnnotationTestSuite;
import org.easyrules.core.test.composite.CompositeRuleTest;
import org.easyrules.core.test.jmx.JmxRuleRegistrationTest;
import org.easyrules.core.test.parameters.DefaultRulesEngineTest;
import org.easyrules.core.test.parameters.RulePriorityComparisonTest;
import org.easyrules.core.test.parameters.RulePriorityThresholdTest;
import org.easyrules.core.test.parameters.SkipOnFirstAppliedRuleTest;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for Easy Rules core module.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */

@RunWith(Suite.class)
@Suite.SuiteClasses( {
        RulePriorityComparisonTest.class,
        RulePriorityThresholdTest.class,
        SkipOnFirstAppliedRuleTest.class,
        JmxRuleRegistrationTest.class,
        CompositeRuleTest.class,
        EasyRulesAnnotationTestSuite.class,
        DefaultRulesEngineTest.class})
public class EasyRulesTestSuite extends TestSuite {

}

package io.github.benas.easyrules.core.test;

import io.github.benas.easyrules.core.test.annotation.EasyRulesAnnotationTestSuite;
import io.github.benas.easyrules.core.test.composite.CompositeRuleTest;
import io.github.benas.easyrules.core.test.jmx.JmxRuleRegistrationTest;
import io.github.benas.easyrules.core.test.parameters.RulePriorityComparisonTest;
import io.github.benas.easyrules.core.test.parameters.RulePriorityThresholdTest;
import io.github.benas.easyrules.core.test.parameters.SkipOnFirstAppliedRuleTest;
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
        EasyRulesAnnotationTestSuite.class})
public class EasyRulesTestSuite extends TestSuite {

}

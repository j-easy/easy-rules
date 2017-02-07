package org.easyrules;

import junit.framework.TestSuite;
import org.easyrules.core.*;
import org.easyrules.util.UtilsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for Easy Rules core module.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BasicRuleTest.class,
        RulePriorityThresholdTest.class,
        SkipOnFirstAppliedRuleTest.class,
        SkipOnFirstFailedRuleTest.class,
        SkipOnFirstNonTrigeredRuleTest.class,
        RuleListenerTest.class,
        CustomRuleOrderingTest.class,
        RuleProxyTest.class,
        RulesEngineBuilderTest.class,
        CompositeRuleTest.class,
        RuleDefinitionValidatorTest.class,
        DefaultRulesEngineTest.class,
        UtilsTest.class})
public class EasyRulesTestSuite extends TestSuite {

}

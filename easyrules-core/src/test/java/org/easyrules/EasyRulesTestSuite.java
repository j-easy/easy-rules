package org.easyrules;

import junit.framework.TestSuite;
import org.easyrules.core.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for Easy Rules core module.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RulePriorityThresholdTest.class,
        SkipOnFirstAppliedRuleTest.class,
        SkipOnFirstFailedRuleTest.class,
        RuleListenerTest.class,
        CustomRuleOrderingTest.class,
        CompositeRuleTest.class,
        RuleDefinitionValidatorTest.class,
        DefaultRulesEngineTest.class})
public class EasyRulesTestSuite extends TestSuite {

}

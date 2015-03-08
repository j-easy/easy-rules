package org.easyrules;

import junit.framework.TestSuite;
import org.easyrules.annotation.ActionMethodDefinitionTest;
import org.easyrules.annotation.ConditionMethodDefinitionTest;
import org.easyrules.annotation.PriorityMethodDefinitionTest;
import org.easyrules.core.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for Easy Rules core module.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */

@RunWith(Suite.class)
@Suite.SuiteClasses( {
        RulePriorityComparisonTest.class,
        RulePriorityThresholdTest.class,
        SkipOnFirstAppliedRuleTest.class,
        CompositeRuleTest.class,
        ConditionMethodDefinitionTest.class,
        ActionMethodDefinitionTest.class,
        PriorityMethodDefinitionTest.class,
        AnnotatedRulesEngineTest.class,
        DefaultRulesEngineTest.class})
public class EasyRulesTestSuite extends TestSuite {

}

package org.easyrules.core.test.annotation;

import junit.framework.TestSuite;
import org.easyrules.core.test.annotation.action.ActionMethodDefinitionTest;
import org.easyrules.core.test.annotation.condition.ConditionMethodDefinitionTest;
import org.easyrules.core.test.annotation.priority.PriorityMethodDefinitionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for Easy Rules annotations.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */

@RunWith(Suite.class)
@Suite.SuiteClasses( {
        ActionMethodDefinitionTest.class,
        ConditionMethodDefinitionTest.class,
        PriorityMethodDefinitionTest.class,
        AnnotatedRulesEngineTest.class})
public class EasyRulesAnnotationTestSuite extends TestSuite {

}

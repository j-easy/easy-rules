package org.easyrules.core.test.annotation;

import org.easyrules.core.AnnotatedRulesEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for annotated rules engine execution.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class AnnotatedRulesEngineTest {

    private SimpleAnnotatedRule simpleAnnotatedRule;

    private AnnotatedRulesEngine rulesEngine;

    @Before
    public void setup(){
        simpleAnnotatedRule = new SimpleAnnotatedRule();
        rulesEngine = new AnnotatedRulesEngine();
    }

    @Test
    public void wellAnnotatedRuleShouldBeExecuted() {
        rulesEngine.registerRule(simpleAnnotatedRule);
        rulesEngine.fireRules();
        //The annotated rule should be executed
        assertEquals(true, simpleAnnotatedRule.isExecuted());

    }

    @Test(expected = IllegalArgumentException.class)
    public void notAnnotatedRuleMustNotBeAccepted() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new Object());
    }

    @Test
    public void actionsMustBeExecutedInTheDefinedOrder() {
        rulesEngine.registerRule(simpleAnnotatedRule);
        rulesEngine.fireRules();
        //Actions must be executed in the defined order
        assertEquals("012", simpleAnnotatedRule.getActionSequence());
    }

    @After
    public void clearRules() {
        rulesEngine.clearRules();
    }

}

package io.github.benas.easyrules.core.test;

import io.github.benas.easyrules.api.RulesEngine;
import io.github.benas.easyrules.core.PriorityRulesEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for composite priority rule execution.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class CompositePriorityRuleTest {

    private SimplePriorityRule rule1, rule2;

    private SimpleCompositePriorityRule compositeRule;

    private RulesEngine rulesEngine;

    @Before
    public void setup(){

        rule1 = new SimplePriorityRule("r1","d1",1);
        rule2 = new SimplePriorityRule("r2","d2",2);

        compositeRule = new SimpleCompositePriorityRule("cp", "crd");

        rulesEngine = new PriorityRulesEngine();
    }

    @Test
    public void testCompositeRule() {

        compositeRule.addRule(rule1);
        compositeRule.addRule(rule2);

        rulesEngine.registerRule(compositeRule);

        rulesEngine.fireRules();

        //Rule 1 should be executed
        assertEquals(true, rule1.isExecuted());

        //Rule 2 should be executed
        assertEquals(true, rule2.isExecuted());

        //The composite Rule should be executed
        assertEquals(true, compositeRule.isExecuted());

    }

    @Test
    public void testCompositeRuleWithARuleThatEvaluateToFalse() {

        compositeRule.addRule(rule1);

        rule2 = new SimplePriorityRuleThatEvaluateToFalse("r2","d2",2);

        compositeRule.addRule(rule2);

        rulesEngine.registerRule(compositeRule);

        rulesEngine.fireRules();

        //The composite rule and composing rules should not be executed since not all rules conditions evaluate to TRUE

        //Rule 1 should not be executed
        assertEquals(false, rule1.isExecuted());

        //Rule 2 should not be executed
        assertEquals(false, rule2.isExecuted());

        //The composite Rule not should be executed
        assertEquals(false, compositeRule.isExecuted());

    }

}

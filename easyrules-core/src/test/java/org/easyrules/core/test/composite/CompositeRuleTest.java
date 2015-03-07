package org.easyrules.core.test.composite;

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.DefaultRulesEngine;
import org.easyrules.core.test.SimpleRule;
import org.easyrules.core.test.SimpleRuleThatEvaluateToFalse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for composite rule execution.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class CompositeRuleTest {

    private SimpleRule rule1, rule2;

    private SimpleCompositeRule compositeRule;

    private RulesEngine<Rule> rulesEngine;

    @Before
    public void setup(){

        rule1 = new SimpleRule("r1","d1");
        rule2 = new SimpleRule("r2","d2");

        compositeRule = new SimpleCompositeRule("cp", "crd");

        rulesEngine = new DefaultRulesEngine();
    }

    @Test
    public void compositeRuleAndComposingRulesMustBeExecuted() {

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
    public void compositeRuleMustNotBeExecutedIfAComposingRuleEvaluatesToFalse() {

        compositeRule.addRule(rule1);

        rule2 = new SimpleRuleThatEvaluateToFalse("r2","d2");

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

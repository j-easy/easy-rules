package org.easyrules.core;

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Test class for composite rule execution.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@RunWith(MockitoJUnitRunner.class)
public class CompositeRuleTest {

    @Mock
    private BasicRule rule1, rule2;

    private CompositeRule compositeRule;

    private RulesEngine<Rule> rulesEngine;

    @Before
    public void setup(){

        when(rule1.getName()).thenReturn("r1");
        when(rule1.evaluateConditions()).thenReturn(true);

        when(rule2.getName()).thenReturn("r2");
        when(rule2.evaluateConditions()).thenReturn(true);

        compositeRule = new CompositeRule("cr");

        rulesEngine = new DefaultRulesEngine();
    }

    @Test
    public void compositeRuleAndComposingRulesMustBeExecuted() throws Exception {

        compositeRule.addRule(rule1);
        compositeRule.addRule(rule2);

        rulesEngine.registerRule(compositeRule);

        rulesEngine.fireRules();

        //Rule 1 should be executed
        verify(rule1).performActions();

        //Rule 2 should be executed
        verify(rule2).performActions();

    }

    @Test
    public void compositeRuleMustNotBeExecutedIfAComposingRuleEvaluatesToFalse() throws Exception {

        when(rule2.evaluateConditions()).thenReturn(false);

        compositeRule.addRule(rule1);
        compositeRule.addRule(rule2);

        rulesEngine.registerRule(compositeRule);

        rulesEngine.fireRules();

        /*
         * The composing rules should not be executed
         * since not all rules conditions evaluate to TRUE
         */

        //Rule 1 should not be executed
        verify(rule1, never()).performActions();

        //Rule 2 should not be executed
        verify(rule2, never()).performActions();

    }

}

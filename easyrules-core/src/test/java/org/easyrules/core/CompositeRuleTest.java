/**
 * The MIT License
 *
 *  Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.easyrules.core;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.easyrules.api.RulesEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;
import static org.mockito.Mockito.*;

/**
 * Test class for composite rule execution.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class CompositeRuleTest {

    @Mock
    private BasicRule rule1, rule2;

    private CompositeRule compositeRule;

    private RulesEngine rulesEngine;

    @Before
    public void setup() throws Exception {

        when(rule1.evaluate()).thenReturn(true);
        when(rule2.evaluate()).thenReturn(true);
        when(rule2.compareTo(rule1)).thenReturn(1);

        compositeRule = new CompositeRule();

        rulesEngine = aNewRulesEngine().build();
    }

    @Test
    public void compositeRuleAndComposingRulesMustBeExecuted() throws Exception {

        compositeRule.addRule(rule1);
        compositeRule.addRule(rule2);

        rulesEngine.registerRule(compositeRule);

        rulesEngine.fireRules();

        verify(rule1).execute();

        verify(rule2).execute();

    }

    @Test
    public void compositeRuleMustNotBeExecutedIfAComposingRuleEvaluatesToFalse() throws Exception {

        when(rule2.evaluate()).thenReturn(false);

        compositeRule.addRule(rule1);
        compositeRule.addRule(rule2);

        rulesEngine.registerRule(compositeRule);

        rulesEngine.fireRules();

        /*
         * The composing rules should not be executed
         * since not all rules conditions evaluate to TRUE
         */

        //Rule 1 should not be executed
        verify(rule1, never()).execute();

        //Rule 2 should not be executed
        verify(rule2, never()).execute();

    }

    @Test
    public void whenARuleIsRemoved_thenItShouldNotBeEvaluated() throws Exception {

        compositeRule.addRule(rule1);
        compositeRule.addRule(rule2);
        compositeRule.removeRule(rule2);

        rulesEngine.registerRule(compositeRule);

        rulesEngine.fireRules();

        //Rule 1 should be executed
        verify(rule1).execute();

        //Rule 2 should not be evaluated nor executed
        verify(rule2, never()).evaluate();
        verify(rule2, never()).execute();

    }

    @Test
    public void whenNoComposingRulesAreRegistered_thenCompositeRuleShouldEvaluateToFalse() {
        assertThat(compositeRule.evaluate()).isFalse();
    }

    @Test
    public void testCompositeRuleWithAnnotatedComposingRules() throws Exception {
        MyRule rule = new MyRule();
        compositeRule = new CompositeRule("myCompositeRule");
        compositeRule.addRule(rule);

        rulesEngine.registerRule(compositeRule);
        rulesEngine.fireRules();

        assertThat(rule.isExecuted()).isTrue();
    }

    @Test
    public void whenAnnotatedRuleIsRemoved_thenItsProxyShouldBeRetrieved() throws Exception {
        MyRule rule = new MyRule();
        MyAnnotatedRule annotatedRule = new MyAnnotatedRule();
        compositeRule = new CompositeRule("myCompositeRule", "composite rule with mixed types of rules");
        compositeRule.addRule(rule);
        compositeRule.addRule(annotatedRule);
        compositeRule.removeRule(annotatedRule);

        rulesEngine.registerRule(compositeRule);
        rulesEngine.fireRules();

        assertThat(rule.isExecuted()).isTrue();
        assertThat(annotatedRule.isExecuted()).isFalse();
    }

    @org.easyrules.annotation.Rule
    class MyRule {
        boolean executed;
        @Condition
        public boolean when() {
            return true;
        }
        @Action
        public void then() {
            executed = true;
        }
        public boolean isExecuted() {
            return executed;
        }
    }

    @Rule
    static class MyAnnotatedRule {
        private boolean executed;
        @Condition
        public boolean evaluate() {
            return true;
        }
        @Action
        public void execute() {
            executed = true;
        }
        public boolean isExecuted() {
            return executed;
        }
    }
}

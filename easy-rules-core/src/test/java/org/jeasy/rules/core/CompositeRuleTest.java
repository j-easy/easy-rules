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
package org.jeasy.rules.core;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Rule;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Test class for composite rule execution.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class CompositeRuleTest extends AbstractTest {

    private CompositeRule compositeRule;

    @Before
    public void setup() throws Exception {
        super.setup();
        when(rule1.evaluate(facts)).thenReturn(true);
        when(rule2.evaluate(facts)).thenReturn(true);
        when(rule2.compareTo(rule1)).thenReturn(1);
        compositeRule = new CompositeRule();
    }

    @Test
    public void compositeRuleAndComposingRulesMustBeExecuted() throws Exception {
        compositeRule.addRule(rule1);
        compositeRule.addRule(rule2);
        rules.clear(); // FIXME
        rules.register(compositeRule);
        rulesEngine.fire(rules, facts);
        verify(rule1).execute(facts);
        verify(rule2).execute(facts);
    }

    @Test
    public void compositeRuleMustNotBeExecutedIfAComposingRuleEvaluatesToFalse() throws Exception {
        when(rule2.evaluate(facts)).thenReturn(false);
        compositeRule.addRule(rule1);
        compositeRule.addRule(rule2);
        rules.clear(); // FIXME
        rules.register(compositeRule);
        rulesEngine.fire(rules, facts);

        /*
         * The composing rules should not be executed
         * since not all rules conditions evaluate to TRUE
         */

        //Rule 1 should not be executed
        verify(rule1, never()).execute(facts);

        //Rule 2 should not be executed
        verify(rule2, never()).execute(facts);

    }

    @Test
    public void whenARuleIsRemoved_thenItShouldNotBeEvaluated() throws Exception {

        compositeRule.addRule(rule1);
        compositeRule.addRule(rule2);
        compositeRule.removeRule(rule2);

        rules.clear(); // FIXME
        rules.register(compositeRule);

        rulesEngine.fire(rules, facts);

        //Rule 1 should be executed
        verify(rule1).execute(facts);

        //Rule 2 should not be evaluated nor executed
        verify(rule2, never()).evaluate(facts);
        verify(rule2, never()).execute(facts);

    }

    @Test
    public void whenNoComposingRulesAreRegistered_thenCompositeRuleShouldEvaluateToFalse() {
        assertThat(compositeRule.evaluate(facts)).isFalse();
    }

    @Test
    public void testCompositeRuleWithAnnotatedComposingRules() throws Exception {
        MyRule rule = new MyRule();
        compositeRule = new CompositeRule("myCompositeRule");
        compositeRule.addRule(rule);

        rules.register(compositeRule);;
        rulesEngine.fire(rules, facts);

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

        rules.register(compositeRule);
        rulesEngine.fire(rules, facts);

        assertThat(rule.isExecuted()).isTrue();
        assertThat(annotatedRule.isExecuted()).isFalse();
    }

    @Rule
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

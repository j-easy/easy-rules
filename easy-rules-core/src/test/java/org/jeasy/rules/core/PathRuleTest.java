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
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for path rule execution.
 *
 * @author Dag Framstad (dagframstad@gmail.com)
 */
public class PathRuleTest extends AbstractTest {

    private PathRule pathRule;
    @Mock
    private org.jeasy.rules.api.Rule primaryRule;
    @Mock
    private Object primaryFacts;

    @Before
    public void setup() throws Exception {
        super.setup();

        when(rule1.evaluate(facts)).thenReturn(false);
        when(rule2.evaluate(facts)).thenReturn(true);
        when(rule2.compareTo(rule1)).thenReturn(1);
        pathRule = new PathRule(primaryRule);
    }

    @Test
    public void pathRulesMustNotBeExecutedIfPrimaryRuleEvaluatesToFalse() throws Exception {
        // Given
        when(primaryRule.evaluate(facts)).thenReturn(false);
        pathRule.addRule(rule1);
        pathRule.addRule(rule2);
        rules.register(pathRule);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        /*
         * The composing rules should not be executed
         * since the primary rule evaluate to FALSE
         */

        // primaryRule should not be executed
        verify(primaryRule, never()).execute(facts);
        //Rule 1 should not be executed
        verify(rule1, never()).execute(facts);
        //Rule 2 should not be executed
        verify(rule2, never()).execute(facts);
    }

    @Test
    public void pathRulesMustBeExecutedForThoseThatEvaluateToTrueIfPrimaryRuleEvaluatesToTrue() throws Exception {
        // Given
        when(primaryRule.evaluate(facts)).thenReturn(true);
        pathRule.addRule(rule1);
        pathRule.addRule(rule2);
        rules.register(pathRule);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        /*
         * Some of he composing rules should be executed
         * since the primary rule evaluate to TRUE
         */

        // primaryRule should be executed
        verify(primaryRule, times(1)).execute(facts);
        //Rule 1 should not be executed
        verify(rule1, never()).execute(facts);
        //Rule 2 should be executed
        verify(rule2, times(1)).execute(facts);
    }

    @Test
    public void whenARuleIsRemoved_thenItShouldNotBeEvaluated() throws Exception {
        // Given
        when(primaryRule.evaluate(facts)).thenReturn(true);
        pathRule.addRule(rule1);
        pathRule.addRule(rule2);
        pathRule.removeRule(rule2);
        rules.register(pathRule);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        // primaryRule should be executed
        verify(primaryRule, times(1)).execute(facts);
        //Rule 1 should not be executed
        verify(rule1, times(1)).evaluate(facts);
        verify(rule1, never()).execute(facts);
        // Rule 2 should not be evaluated nor executed
        verify(rule2, never()).evaluate(facts);
        verify(rule2, never()).execute(facts);

    }

    @Test
    public void whenNoComposingRulesAreRegistered_thenPathRuleShouldBehaveAsANormalRule() throws Exception {
        when(primaryRule.evaluate(facts)).thenReturn(true);
        pathRule = new PathRule(primaryRule);
        rules.register(pathRule);

        // When
        rulesEngine.fire(rules, facts);
        verify(primaryRule, times(1)).execute(facts);

    }

    @Test
    public void testCompositeRuleWithAnnotatedComposingRules() throws Exception {
        // Given
        when(primaryRule.evaluate(facts)).thenReturn(true);
        MyRule rule = new MyRule();
        pathRule = new PathRule("myPathRule", primaryRule);
        pathRule.addRule(rule);
        rules.register(pathRule);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        verify(primaryRule, times(1)).execute(facts);
        assertThat(rule.isExecuted()).isTrue();
    }

    @Test
    public void whenAnnotatedRuleIsRemoved_thenItsProxyShouldBeRetrieved() throws Exception {
        // Given
        when(primaryRule.evaluate(facts)).thenReturn(true);
        MyRule rule = new MyRule();
        MyAnnotatedRule annotatedRule = new MyAnnotatedRule();
        pathRule = new PathRule("myCompositeRule", "composite rule with mixed types of rules", primaryRule);
        pathRule.addRule(rule);
        pathRule.addRule(annotatedRule);
        pathRule.removeRule(annotatedRule);
        rules.register(pathRule);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        verify(primaryRule, times(1)).execute(facts);
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

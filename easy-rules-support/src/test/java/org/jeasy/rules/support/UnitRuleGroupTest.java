/**
 * The MIT License
 *
 *  Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.support;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnitRuleGroupTest {

    @Mock
    private Rule rule1, rule2;

    private Facts facts = new Facts();
    private Rules rules = new Rules();

    private DefaultRulesEngine rulesEngine = new DefaultRulesEngine();

    private org.jeasy.rules.support.UnitRuleGroup unitRuleGroup;


    @Before
    public void setUp() throws Exception {
        when(rule1.evaluate(facts)).thenReturn(true);
        when(rule2.evaluate(facts)).thenReturn(true);
        when(rule2.compareTo(rule1)).thenReturn(1);
    }

    @Test
    public void whenNoComposingRulesAreRegistered_thenUnitRuleGroupShouldEvaluateToFalse() {
        // given
        unitRuleGroup = new UnitRuleGroup();

        // when
        boolean evaluationResult = unitRuleGroup.evaluate(facts);

        // then
        assertThat(evaluationResult).isFalse();
    }

    @Test
    public void compositeRuleAndComposingRulesMustBeExecuted() throws Exception {
        // Given
        unitRuleGroup = new UnitRuleGroup();
        unitRuleGroup.addRule(rule1);
        unitRuleGroup.addRule(rule2);
        rules.register(unitRuleGroup);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        verify(rule1).execute(facts);
        verify(rule2).execute(facts);
    }

    @Test
    public void compositeRuleMustNotBeExecutedIfAComposingRuleEvaluatesToFalse() throws Exception {
        // Given
        when(rule2.evaluate(facts)).thenReturn(false);
        unitRuleGroup = new UnitRuleGroup();
        unitRuleGroup.addRule(rule1);
        unitRuleGroup.addRule(rule2);
        rules.register(unitRuleGroup);

        // When
        rulesEngine.fire(rules, facts);

        // Then
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
        // Given
        unitRuleGroup = new UnitRuleGroup();
        unitRuleGroup.addRule(rule1);
        unitRuleGroup.addRule(rule2);
        unitRuleGroup.removeRule(rule2);
        rules.register(unitRuleGroup);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        //Rule 1 should be executed
        verify(rule1).execute(facts);

        //Rule 2 should not be evaluated nor executed
        verify(rule2, never()).evaluate(facts);
        verify(rule2, never()).execute(facts);
    }

    @Test
    public void testCompositeRuleWithAnnotatedComposingRules() throws Exception {
        // Given
        MyRule rule = new MyRule();
        unitRuleGroup = new UnitRuleGroup();
        unitRuleGroup.addRule(rule);
        rules.register(unitRuleGroup);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(rule.isExecuted()).isTrue();
    }

    @Test
    public void whenAnnotatedRuleIsRemoved_thenItsProxyShouldBeRetrieved() throws Exception {
        // Given
        MyRule rule = new MyRule();
        MyAnnotatedRule annotatedRule = new MyAnnotatedRule();
        unitRuleGroup = new UnitRuleGroup();
        unitRuleGroup.addRule(rule);
        unitRuleGroup.addRule(annotatedRule);
        unitRuleGroup.removeRule(annotatedRule);
        rules.register(unitRuleGroup);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(rule.isExecuted()).isTrue();
        assertThat(annotatedRule.isExecuted()).isFalse();
    }

    @org.jeasy.rules.annotation.Rule
    public class MyRule {
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

    @org.jeasy.rules.annotation.Rule
    public static class MyAnnotatedRule {
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

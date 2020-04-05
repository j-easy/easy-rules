/*
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
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.BasicRule;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionalRuleGroupTest {

    private static List<String> actions = new ArrayList<>();

    private TestRule rule1, rule2, conditionalRule;
    private ConditionalRuleGroup conditionalRuleGroup;

    private Facts facts = new Facts();
    private Rules rules = new Rules();

    private DefaultRulesEngine rulesEngine = new DefaultRulesEngine();

    @Before
    public void setUp() {
        conditionalRule = new TestRule("conditionalRule", "description0", 0, true);
        rule1 = new TestRule("rule1", "description1", 1, true);
        rule2 = new TestRule("rule2", "description2", 2, true);
        conditionalRuleGroup = new ConditionalRuleGroup();
        conditionalRuleGroup.addRule(rule1);
        conditionalRuleGroup.addRule(rule2);           
        conditionalRuleGroup.addRule(conditionalRule);
        rules.register(conditionalRuleGroup);
    }

    @After
    public void tearDown() {
        rules.clear();
        actions.clear();
    }

    @Test
    public void rulesMustNotBeExecutedIfConditionalRuleEvaluatesToFalse() {
        // Given
        conditionalRule.setEvaluationResult(false);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        /*
         * The composing rules should not be executed
         * since the conditional rule evaluate to FALSE
         */

        // primaryRule should not be executed
        assertThat(conditionalRule.isExecuted()).isFalse();
        //Rule 1 should not be executed
        assertThat(rule1.isExecuted()).isFalse();
        //Rule 2 should not be executed
        assertThat(rule2.isExecuted()).isFalse();
    }

    @Test
    public void selectedRulesMustBeExecutedIfConditionalRuleEvaluatesToTrue() {
        // Given
        rule1.setEvaluationResult(false);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        /*
         * Selected composing rules should be executed
         * since the conditional rule evaluates to TRUE
         */

        // primaryRule should be executed
        assertThat(conditionalRule.isExecuted()).isTrue();
        //Rule 1 should not be executed
        assertThat(rule1.isExecuted()).isFalse();
        //Rule 2 should be executed
        assertThat(rule2.isExecuted()).isTrue();
    }

    @Test
    public void whenARuleIsRemoved_thenItShouldNotBeEvaluated() {
        // Given
        conditionalRuleGroup.removeRule(rule2);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        // primaryRule should be executed
        assertThat(conditionalRule.isExecuted()).isTrue();
        //Rule 1 should be executed
        assertThat(rule1.isExecuted()).isTrue();
        // Rule 2 should not be executed
        assertThat(rule2.isExecuted()).isFalse();
    }

    @Test
    public void testCompositeRuleWithAnnotatedComposingRules() {
        // Given
        MyRule rule = new MyRule();
        conditionalRuleGroup.addRule(rule);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(conditionalRule.isExecuted()).isTrue();
        assertThat(rule.isExecuted()).isTrue();
    }

    @Test
    public void whenAnnotatedRuleIsRemoved_thenItsProxyShouldBeRetrieved() {
        // Given
        MyRule rule = new MyRule();
        MyAnnotatedRule annotatedRule = new MyAnnotatedRule();
        conditionalRuleGroup.addRule(rule);
        conditionalRuleGroup.addRule(annotatedRule);
        conditionalRuleGroup.removeRule(annotatedRule);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(conditionalRule.isExecuted()).isTrue();
        assertThat(rule.isExecuted()).isTrue();
        assertThat(annotatedRule.isExecuted()).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void twoRulesWithSameHighestPriorityIsNotAllowed() {
        conditionalRuleGroup.addRule(new MyOtherRule(0));// same priority as conditionalRule
        conditionalRuleGroup.addRule(new MyOtherRule(1));
        conditionalRuleGroup.addRule(new MyRule());
        rulesEngine.fire(rules, facts);
    }

    @Test
    public void twoRulesWithSamePriorityIsAllowedIfAnotherRuleHasHigherPriority() {
        MyOtherRule rule1 = new MyOtherRule(3);
        conditionalRuleGroup.addRule(rule1);
        conditionalRuleGroup.addRule(new MyOtherRule(2));
        conditionalRuleGroup.addRule(new MyRule());
        rules.register(conditionalRuleGroup);
        rulesEngine.fire(rules, facts);
        assertThat(rule1.isExecuted()).isTrue();
    }

    @Test
    public void aRuleWithoutPriorityHasLowestPriority() {
        // given
        UnprioritizedRule rule = new UnprioritizedRule();
        conditionalRuleGroup.addRule(rule);

        // when
        rulesEngine.fire(rules, facts);

        // then
        assertThat(actions).containsExactly(
                "conditionalRule",
                "rule1",
                "rule2",
                "UnprioritizedRule");
    }

    @Test
    public void testComposingRulesExecutionOrder() {
        // When
        rulesEngine.fire(rules, facts);

        // Then
        // rule 1 has higher priority than rule 2 (lower values for highers priorities),
        // it should be executed first
        assertThat(actions).containsExactly(
                "conditionalRule",
                "rule1",
                "rule2");
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

        @Priority
        public int priority() {
            return 2;
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

        @Priority
        public int priority() {
            return 3;
        }

        public boolean isExecuted() {
            return executed;
        }
    }

    @org.jeasy.rules.annotation.Rule
    public class MyOtherRule {
        boolean executed;
        private int priority;

        public MyOtherRule(int priority) {
            this.priority = priority;
        }

        @Condition
        public boolean when() {
            return true;
        }

        @Action
        public void then() {
            executed = true;
        }

        @Priority
        public int priority() {
            return priority;
        }

        public boolean isExecuted() {
            return executed;
        }

    }

    @org.jeasy.rules.annotation.Rule
    public class UnprioritizedRule {
        boolean executed;

        @Condition
        public boolean when() {
            return true;
        }

        @Action
        public void then() {
            executed = true;
            actions.add("UnprioritizedRule");
        }

        public boolean isExecuted() {
            return executed;
        }

    }

    public class TestRule extends BasicRule {

        boolean executed;
        boolean evaluationResult;

        TestRule(String name, String description, int priority, boolean evaluationResult) {
            super(name, description, priority);
            this.evaluationResult = evaluationResult;
        }

        @Override
        public boolean evaluate(Facts facts) {
            return evaluationResult;
        }

        @Override
        public void execute(Facts facts) {
            this.executed = true;
            actions.add(name);
        }

        boolean isExecuted() {
            return executed;
        }

        void setEvaluationResult(boolean evaluationResult) {
            this.evaluationResult = evaluationResult;
        }
    }
}

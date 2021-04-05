/*
 * The MIT License
 *
 *  Copyright (c) 2021, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package org.jeasy.rules.support.composite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SequentialRuleGroupTest {

  @Mock
  private Rule rule1, rule2;

  private Facts facts = new Facts();
  private Rules rules = new Rules();

  private DefaultRulesEngine rulesEngine = new DefaultRulesEngine();

  private SequentialRuleGroup sequentialRuleGroup;

  @Before
  public void setUp() {
    when(rule1.evaluate(facts)).thenReturn(true);
    when(rule2.evaluate(facts)).thenReturn(true);
    when(rule2.compareTo(rule1)).thenReturn(-1);
  }

  @Test
  public void whenNoComposingRulesAreRegistered_thenSequentialRuleGroupShouldEvaluateToTrue() {
    // given
    sequentialRuleGroup = new SequentialRuleGroup();

    // when
    boolean evaluationResult = sequentialRuleGroup.evaluate(facts);

    // then
    assertThat(evaluationResult).isTrue();
  }

  @Test
  public void compositeRuleAndComposingRulesMustBeExecuted() throws Exception {
    // Given
    sequentialRuleGroup = new SequentialRuleGroup();
    sequentialRuleGroup.addRule(rule1);
    sequentialRuleGroup.addRule(rule2);
    rules.register(sequentialRuleGroup);

    // When
    rulesEngine.fire(rules, facts);

    // Then
    verify(rule1).execute(facts);
    verify(rule2).execute(facts);
  }

  @Test
  public void compositeRuleAndComposingRulesMustBeExecutedInOrder() throws Exception {
    // Given
    sequentialRuleGroup = new SequentialSortedRuleGroup();
    sequentialRuleGroup.addRule(rule1);
    sequentialRuleGroup.addRule(rule2);
    rules.register(sequentialRuleGroup);

    InOrder inOrder = inOrder(rule2, rule1);

    // When
    rulesEngine.fire(rules, facts);

    inOrder.verify(rule2).execute(facts);
    inOrder.verify(rule1).execute(facts);
  }

  @Test
  public void allConstituentRulesThatEvaluateToTrueMustBeExecutedAndThoseEvaluatingToFalseSkipped()
      throws Exception {
    // Given
    when(rule2.evaluate(facts)).thenReturn(false);
    sequentialRuleGroup = new SequentialRuleGroup();
    sequentialRuleGroup.addRule(rule1);
    sequentialRuleGroup.addRule(rule2);
    rules.register(sequentialRuleGroup);

    // When
    rulesEngine.fire(rules, facts);

    // Then
    /*
     * The composing rules should not be executed
     * since not all rules conditions evaluate to TRUE
     */

    //Rule 1 should not be executed
    verify(rule1).execute(facts);
    //Rule 2 should not be executed
    verify(rule2, never()).execute(facts);
  }

  @Test
  public void whenARuleIsRemoved_thenItShouldNotBeEvaluated() throws Exception {
    // Given
    sequentialRuleGroup = new SequentialRuleGroup();
    sequentialRuleGroup.addRule(rule1);
    sequentialRuleGroup.addRule(rule2);
    sequentialRuleGroup.removeRule(rule2);
    rules.register(sequentialRuleGroup);

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
  public void testCompositeRuleWithAnnotatedComposingRules() {
    // Given
    MyRule rule = new MyRule();
    sequentialRuleGroup = new SequentialRuleGroup();
    sequentialRuleGroup.addRule(rule);
    rules.register(sequentialRuleGroup);

    // When
    rulesEngine.fire(rules, facts);

    // Then
    assertThat(rule.isExecuted()).isTrue();
  }

  @Test
  public void whenAnnotatedRuleIsRemoved_thenItsProxyShouldBeRetrieved() {
    // Given
    MyRule rule = new MyRule();
    MyAnnotatedRule annotatedRule = new MyAnnotatedRule();
    sequentialRuleGroup = new SequentialRuleGroup();
    sequentialRuleGroup.addRule(rule);
    sequentialRuleGroup.addRule(annotatedRule);
    sequentialRuleGroup.removeRule(annotatedRule);
    rules.register(sequentialRuleGroup);

    // When
    rulesEngine.fire(rules, facts);

    // Then
    assertThat(rule.isExecuted()).isTrue();
    assertThat(annotatedRule.isExecuted()).isFalse();
  }

  @org.jeasy.rules.annotation.Rule
  public static class MyRule {

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

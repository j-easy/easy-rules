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

import org.easyrules.api.RuleListener;
import org.easyrules.api.RulesEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;
import static org.mockito.Mockito.*;

/**
 * Test class of the execution of rule listeners.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class RuleListenerTest {

    @Mock
    private BasicRule rule;

    @Mock
    private RuleListener ruleListener1, ruleListener2;

    private RulesEngine rulesEngine;

    @Before
    public void setup() throws Exception {
        when(rule.getName()).thenReturn("r");
        when(rule.getPriority()).thenReturn(1);
        when(rule.evaluate()).thenReturn(true);
        when(ruleListener1.beforeEvaluate(rule)).thenReturn(true);
        when(ruleListener2.beforeEvaluate(rule)).thenReturn(true);

        rulesEngine = aNewRulesEngine()
                .withRuleListener(ruleListener1)
                .withRuleListener(ruleListener2)
                .build();
    }

    @Test
    public void whenTheRuleExecutesSuccessfully_thenOnSuccessShouldBeExecuted() throws Exception {

        rulesEngine.registerRule(rule);

        rulesEngine.fireRules();

        InOrder inOrder = inOrder(ruleListener1, ruleListener2);
        inOrder.verify(ruleListener1).beforeExecute(rule);
        inOrder.verify(ruleListener2).beforeExecute(rule);
        inOrder.verify(ruleListener1).onSuccess(rule);
        inOrder.verify(ruleListener2).onSuccess(rule);

    }

    @Test
    public void whenTheRuleFails_thenOnFailureShouldBeExecuted() throws Exception {

        final Exception exception = new Exception("fatal error!");
        doThrow(exception).when(rule).execute();

        rulesEngine.registerRule(rule);

        rulesEngine.fireRules();

        InOrder inOrder = inOrder(ruleListener1, ruleListener2);
        inOrder.verify(ruleListener1).beforeExecute(rule);
        inOrder.verify(ruleListener2).beforeExecute(rule);
        inOrder.verify(ruleListener1).onFailure(rule, exception);
        inOrder.verify(ruleListener2).onFailure(rule, exception);

    }

    @Test
    public void whenListenerReturnsFalse_thenTheRuleShouldBeSkippedBeforeBeingEvaluated() throws Exception {

        // Given
        when(ruleListener1.beforeEvaluate(rule)).thenReturn(false);
        rulesEngine = aNewRulesEngine()
                .withRuleListener(ruleListener1)
                .build();

        // When
        rulesEngine.registerRule(rule);
        rulesEngine.fireRules();

        // Then
        verify(rule, never()).evaluate();
    }

    @Test
    public void whenListenerReturnsTrue_thenTheRuleShouldBeEvaluated() throws Exception {

        // Given
        when(ruleListener1.beforeEvaluate(rule)).thenReturn(true);
        rulesEngine = aNewRulesEngine()
                .withRuleListener(ruleListener1)
                .build();

        // When
        rulesEngine.registerRule(rule);
        rulesEngine.fireRules();

        // Then
        verify(rule).evaluate();
    }

    @Test
    public void whenTheRuleEvaluatesToTrue_thenTheListenerShouldBeInvoked() throws Exception {
        // Given
        when(rule.evaluate()).thenReturn(true);
        rulesEngine = aNewRulesEngine()
                .withRuleListener(ruleListener1)
                .build();

        // When
        rulesEngine.registerRule(rule);
        rulesEngine.fireRules();

        // Then
        verify(ruleListener1).afterEvaluate(rule, true);
    }

    @Test
    public void whenTheRuleEvaluatesToFalse_thenTheListenerShouldBeInvoked() throws Exception {
        // Given
        when(rule.evaluate()).thenReturn(false);
        rulesEngine = aNewRulesEngine()
                .withRuleListener(ruleListener1)
                .build();

        // When
        rulesEngine.registerRule(rule);
        rulesEngine.fireRules();

        // Then
        verify(ruleListener1).afterEvaluate(rule, false);
    }

}

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
package org.jeasy.rules.core;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class DefaultRuleTest extends AbstractTest {

    @Mock
    private Condition condition;
    @Mock
    private Action action1, action2;

    @Test
    public void WhenConditionIsTrue_ThenActionsShouldBeExecutedInOrder() throws Exception {
        // given
        when(condition.evaluate(facts)).thenReturn(true);
        Rule rule = new RuleBuilder()
                .when(condition)
                .then(action1)
                .then(action2)
                .build();
        rules.register(rule);

        // when
        rulesEngine.fire(rules, facts);

        // then
        InOrder inOrder = Mockito.inOrder(action1, action2);
        inOrder.verify(action1).execute(facts);
        inOrder.verify(action2).execute(facts);
    }

    @Test
    public void WhenConditionIsFalse_ThenActionsShouldNotBeExecuted() throws Exception {
        // given
        when(condition.evaluate(facts)).thenReturn(false);
        Rule rule = new RuleBuilder()
                .when(condition)
                .then(action1)
                .then(action2)
                .build();
        rules.register(rule);

        // when
        rulesEngine.fire(rules, facts);

        // then
        verify(action1, never()).execute(facts);
        verify(action2, never()).execute(facts);
    }
}

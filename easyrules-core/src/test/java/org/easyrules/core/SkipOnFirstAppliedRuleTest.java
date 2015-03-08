/*
 * The MIT License
 *
 *  Copyright (c) 2014, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Test class of "skip on first applied rule" parameter of Easy Rules default engine.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@RunWith(MockitoJUnitRunner.class)
public class SkipOnFirstAppliedRuleTest {

    @Mock
    private BasicRule rule0, rule1, rule2;

    private RulesEngine<Rule> rulesEngine;

    @Before
    public void setup() throws Exception {

        setUpRule0();
        setUpRule1();
        setUpRule2();

        rulesEngine = new DefaultRulesEngine(true);
    }

    @Test
    public void testSkipOnFirstAppliedRule() throws Exception {

        rulesEngine.registerRule(rule1);
        rulesEngine.registerRule(rule2);

        rulesEngine.fireRules();

        //Rule 1 should be executed
        verify(rule1).performActions();

        //Rule 2 should be skipped since Rule 1 has been executed
        verify(rule2, never()).performActions();

    }

    @Test
    public void testSkipOnFirstAppliedRuleWithException() throws Exception {

        rulesEngine.registerRule(rule0);
        rulesEngine.registerRule(rule1);
        rulesEngine.registerRule(rule2);

        rulesEngine.fireRules();

        //If an exception occurs when executing Rule 0, Rule 1 should still be applied
        verify(rule1).performActions();

        //Rule 2 should be skipped since Rule 1 has been executed
        verify(rule2, never()).performActions();

    }

    private void setUpRule2() {
        when(rule2.getName()).thenReturn("r2");
        when(rule2.getPriority()).thenReturn(2);
        when(rule2.evaluateConditions()).thenReturn(true);
        when(rule2.compareTo(rule0)).thenReturn(1);
        when(rule2.compareTo(rule1)).thenReturn(1);
    }

    private void setUpRule1() {
        when(rule1.getName()).thenReturn("r1");
        when(rule1.getPriority()).thenReturn(1);
        when(rule1.evaluateConditions()).thenReturn(true);
        when(rule1.compareTo(rule0)).thenReturn(1);
    }

    private void setUpRule0() throws Exception {
        when(rule0.getName()).thenReturn("r0");
        when(rule0.getPriority()).thenReturn(0);
        when(rule0.evaluateConditions()).thenReturn(true);
        final Exception exception = new Exception("fatal error!");
        doThrow(exception).when(rule0).performActions();
        when(rule0.compareTo(rule1)).thenReturn(-1);
    }

}

/*
 * The MIT License
 *
 *  Copyright (c) 2014, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.easyrules.api.RulesEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Test class of "skip on first failed rule" parameter of Easy Rules default engine.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class SkipOnFirstFailedRuleTest {

    @Mock
    private BasicRule rule1, rule2;

    private RulesEngine rulesEngine;

    @Before
    public void setup() throws Exception {

        setUpRule1();
        setUpRule2();

        rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .withSkipOnFirstFailedRule(true)
                .build();
    }

    @Test
    public void testSkipOnFirstFailedRule() throws Exception {

        rulesEngine.registerRule(rule1);
        rulesEngine.registerRule(rule2);

        rulesEngine.fireRules();

        //Rule 1 should be executed
        verify(rule1).execute();

        //Rule 2 should be skipped since Rule 1 has failed
        verify(rule2, never()).execute();

    }

    private void setUpRule1() throws Exception {
        when(rule1.getName()).thenReturn("r1");
        when(rule1.getPriority()).thenReturn(1);
        when(rule1.evaluate()).thenReturn(true);
        final Exception exception = new Exception("fatal error!");
        doThrow(exception).when(rule1).execute();
    }

    private void setUpRule2() {
        when(rule2.getName()).thenReturn("r2");
        when(rule2.getPriority()).thenReturn(2);
        when(rule2.compareTo(rule1)).thenReturn(1);
    }

}

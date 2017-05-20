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

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Test class of "skip on first non triggered rule" parameter of Easy Rules default engine.
 *
 * @author Krzysztof Kozlowski (krzysztof.kozlowski@coderion.pl)
 */
public class SkipOnFirstNonTriggeredRuleTest extends AbstractTest {

    @Before
    public void setup() throws Exception {
        super.setup();
        setUpRule1();
        rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .withSkipOnFirstNonTriggeredRule(true)
                .build();
    }

    @Test
    public void testSkipOnFirstNonTriggeredRule() throws Exception {

        rulesEngine.fire(rules, facts);

        //Rule1 is non triggered
        verify(rule1, never()).execute(facts);

        //Rule 2 should be skipped since Rule 1 has not been executed
        verify(rule2, never()).execute(facts);

    }

    private void setUpRule1() throws Exception {
        when(rule1.getName()).thenReturn("r1");
        when(rule1.getPriority()).thenReturn(1);
        when(rule1.evaluate(facts)).thenReturn(false);
    }

}

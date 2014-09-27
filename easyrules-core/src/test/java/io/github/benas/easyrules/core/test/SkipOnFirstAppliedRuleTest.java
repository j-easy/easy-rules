/*
 * The MIT License
 *
 *  Copyright (c) 2014, Mahmoud Ben Hassine (md.benhassine@gmail.com)
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

package io.github.benas.easyrules.core.test;

import io.github.benas.easyrules.api.RulesEngine;
import io.github.benas.easyrules.core.PriorityRulesEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class of "skip on first applied rule" parameter of Easy Rules default engine.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class SkipOnFirstAppliedRuleTest {

    private SimplePriorityRule rule1, rule2;

    private SimplePriorityRuleThatThrowsException rule0;

    private RulesEngine rulesEngine;

    @Before
    public void setup(){

        rule1 = new SimplePriorityRule("r1","d1",1);
        rule2 = new SimplePriorityRule("r2","d2",2);

        rule0 = new SimplePriorityRuleThatThrowsException("r0","d0",0);

        rulesEngine = new PriorityRulesEngine(true);
    }

    @Test
    public void testSkipOnFirstAppliedRule() {

        rulesEngine.registerRule(rule1);
        rulesEngine.registerRule(rule2);

        rulesEngine.fireRules();

        //Rule 1 should be executed
        assertEquals(true, rule1.isExecuted());

        //Rule 2 should be skipped since Rule 1 has been executed
        assertEquals(false, rule2.isExecuted());

    }

    @Test
    public void testSkipOnFirstAppliedRuleWithException() {

        rulesEngine.registerRule(rule0);
        rulesEngine.registerRule(rule1);

        rulesEngine.fireRules();

        //If an exception occurs when executing Rule 0, Rule 1 should still be applied

        //Rule 0 should throw an exception, hence not executed
        assertEquals(false, rule0.isExecuted());

        //Rule 1 should be applied since there is no "firstAppliedRule" yet (Rule 0 has not been applied)
        assertEquals(true, rule1.isExecuted());

    }

}

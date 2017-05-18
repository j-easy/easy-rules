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

import org.easyrules.api.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

/**
 * Test class for custom rule ordering.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomRuleOrderingTest extends AbstractTest {

    @Mock
    private MyRule rule1, rule2;

    @Test
    public void whenCompareToIsOverridden_thenShouldExecuteRulesInTheCustomOrder() throws Exception {

        when(rule1.getName()).thenReturn("a");
        when(rule1.getPriority()).thenReturn(1);
        when(rule1.evaluate(facts)).thenReturn(true);

        when(rule2.getName()).thenReturn("b");
        when(rule2.getPriority()).thenReturn(0);
        when(rule2.evaluate(facts)).thenReturn(true);

        when(rule2.compareTo(rule1)).thenCallRealMethod();

        rules.clear();
        rules.register(rule1);
        rules.register(rule2);

        rulesEngine.fire(rules, facts);

        /*
         * By default, if compareTo is not overridden, then rule2 should be executed first (priority 0 < 1).
         * But in this case, the compareTo method order rules by their name, so rule1 should be executed first ("a" < "b")
         */
        InOrder inOrder = inOrder(rule1, rule2);
        inOrder.verify(rule1).execute(facts);
        inOrder.verify(rule2).execute(facts);

    }

    class MyRule extends BasicRule {

        @Override
        public int compareTo(Rule rule) {
            return getName().compareTo(rule.getName());
        }

    }
}

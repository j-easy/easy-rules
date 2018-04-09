/**
 * The MIT License
 *
 *  Copyright (c) 2018, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicRuleTest extends AbstractTest {

    @Test
    public void basicRuleEvaluateShouldReturnFalse() throws Exception {
        BasicRule basicRule = new BasicRule();
        assertThat(basicRule.evaluate(facts)).isFalse();
    }

    @Test
    public void testCompareTo() {
        FirstRule rule1 = new FirstRule();
        FirstRule rule2 = new FirstRule();

        assertThat(rule1.compareTo(rule2)).isEqualTo(0);
        assertThat(rule2.compareTo(rule1)).isEqualTo(0);
    }

    @Test
    public void testSortSequence() {
        FirstRule rule1 = new FirstRule();
        SecondRule rule2 = new SecondRule();
        ThirdRule rule3 = new ThirdRule();

        rules = new Rules(rule1, rule2, rule3);

        rulesEngine.check(rules, facts);
        assertThat(rules).containsSequence(rule1, rule3, rule2);
    }

    class FirstRule extends BasicRule {
        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public boolean evaluate(Facts facts) {
            return true;
        }

        @Override
        public String getName() {
            return "rule1";
        }
    }

    class SecondRule extends BasicRule {
        @Override
        public int getPriority() {
            return 3;
        }

        @Override
        public boolean evaluate(Facts facts) {
            return true;
        }

        @Override
        public String getName() {
            return "rule2";
        }
    }

    class ThirdRule extends BasicRule {
        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public boolean evaluate(Facts facts) {
            return true;
        }

        @Override
        public String getName() {
            return "rule3";
        }
    }

}

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import java.util.Set;

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicRuleTest {

    private static RulesEngine rulesEngine;

    @BeforeClass
    public static void init() {
        rulesEngine = aNewRulesEngine().build();
    }

    @Test
    public void basicRuleEvaluateShouldReturnFalse() throws Exception {
        BasicRule basicRule = new BasicRule();
        assertThat(basicRule.evaluate()).isFalse();
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

        rulesEngine.registerRule(rule1);
        rulesEngine.registerRule(rule2);
        rulesEngine.registerRule(rule3);

        rulesEngine.checkRules();
        Set<Rule> theRules = rulesEngine.getRules();
        assertThat(theRules).containsSequence(rule1, rule3, rule2);
    }

    class FirstRule extends BasicRule {
        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public boolean evaluate() {
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
        public boolean evaluate() {
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
        public boolean evaluate() {
            return true;
        }

        @Override
        public String getName() {
            return "rule3";
        }
    }

}

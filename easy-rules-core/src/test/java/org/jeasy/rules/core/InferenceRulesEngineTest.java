/**
 * The MIT License
 *
 *  Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.jeasy.rules.annotation.*;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InferenceRulesEngineTest {

    @Test
    public void testCandidateSelection() throws Exception {
        // Given
        Facts facts = new Facts();
        facts.put("foo", true);
        DummyRule dummyRule = new DummyRule();
        AnotherDummyRule anotherDummyRule = new AnotherDummyRule();
        Rules rules = new Rules(dummyRule, anotherDummyRule);
        RulesEngine rulesEngine = new InferenceRulesEngine();

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(dummyRule.isExecuted()).isTrue();
        assertThat(anotherDummyRule.isExecuted()).isFalse();
    }

    @Test
    public void testCandidateOrdering() throws Exception {
        // Given
        Facts facts = new Facts();
        facts.put("foo", true);
        facts.put("bar", true);
        DummyRule dummyRule = new DummyRule();
        AnotherDummyRule anotherDummyRule = new AnotherDummyRule();
        Rules rules = new Rules(dummyRule, anotherDummyRule);
        RulesEngine rulesEngine = new InferenceRulesEngine();

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(dummyRule.isExecuted()).isTrue();
        assertThat(anotherDummyRule.isExecuted()).isTrue();
        assertThat(dummyRule.getTimestamp()).isLessThanOrEqualTo(anotherDummyRule.getTimestamp());
    }

    @Rule
    class DummyRule {

        private boolean isExecuted;
        private long timestamp;

        @Condition
        public boolean when(@Fact("foo") boolean foo) {
            return foo;
        }

        @Action
        public void then(Facts facts) {
            isExecuted = true;
            timestamp = System.currentTimeMillis();
            facts.remove("foo");
        }

        @Priority
        public int priority() {
            return 1;
        }

        public boolean isExecuted() {
            return isExecuted;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    @Rule
    class AnotherDummyRule {

        private boolean isExecuted;
        private long timestamp;

        @Condition
        public boolean when(@Fact("bar") boolean bar) {
            return bar;
        }

        @Action
        public void then(Facts facts) {
            isExecuted = true;
            timestamp = System.currentTimeMillis();
            facts.remove("bar");
        }

        @Priority
        public int priority() {
            return 2;
        }

        public boolean isExecuted() {
            return isExecuted;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

}
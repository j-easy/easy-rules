/*
 * The MIT License
 *
 *  Copyright (c) 2021, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Null facts are not accepted by design, a declared fact can be missing though.
 */
public class MissingFactAnnotationParameterTest extends AbstractTest {

    @Test
    public void testMissingFact() {
        Rules rules = new Rules();
        rules.register(new AnnotatedParametersRule());

        Facts facts = new Facts();
        facts.put("fact1", new Object());

        Map<org.jeasy.rules.api.Rule, Boolean> results = rulesEngine.check(rules, facts);

        for (boolean b : results.values()) {
            Assert.assertFalse(b);
        }
    }

    @Rule
    public static class AnnotatedParametersRule {

        @Condition
        public boolean when(@Fact("fact1") Object fact1, @Fact("fact2") Object fact2) {
            return fact1 != null && fact2 == null;
        }

        @Action
        public void then(@Fact("fact1") Object fact1, @Fact("fact2") Object fact2) {
        }

    }
}

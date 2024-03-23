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
package org.jeasy.rules.aviator;

import org.jeasy.rules.api.Facts;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AviatorConditionTest {
    @Test
    public void testAviatorExpressionEvaluation() {
        // given
        AviatorCondition isAdult = new AviatorCondition("person.age > 18");
        Facts facts = new Facts();
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = isAdult.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }

    @Test(expected = RuntimeException.class)
    public void whenDeclaredFactIsNotPresent_thenShouldThrowRuntimeException() {
        // given
        AviatorCondition isHot = new AviatorCondition("temperature > 30");
        Facts facts = new Facts();

        // when
        boolean evaluationResult = isHot.evaluate(facts);

        // then
        // expected exception
    }

    @Test
    public void testAviatorExpressionEvaluationWithJavaUtilClass() {
        AviatorCondition aviatorCondition = new AviatorCondition("let d = new java.util.Random(123); " +
                "return nextBoolean(d)" +
                ";");

        boolean evaluationResult = aviatorCondition.evaluate(new Facts());

        assertThat(evaluationResult).isTrue();
    }

}
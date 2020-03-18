/**
 * The MIT License
 *
 *  Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.spel;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SpELConditionTest {

    @Test
    public void testSpELExpressionEvaluation() {
        // given
        Condition isAdult = new SpELCondition("#person.age > 18");
        Facts facts = new Facts();
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = isAdult.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }

    @Test
    public void whenDeclaredFactIsNotPresent_thenShouldReturnFalse() {
        // given
        Condition isHot = new SpELCondition("#temperature > 30");
        Facts facts = new Facts();

        // when
        boolean evaluationResult = isHot.evaluate(facts);

        // then
        assertThat(evaluationResult).isFalse();
    }

    @Test
    public void testSpELConditionWithExpressionAndParserContext() {
        // given
        ParserContext context = new TemplateParserContext("%{", "}"); // custom parser context
        Condition condition = new SpELCondition("%{ T(java.lang.Integer).MAX_VALUE > 1 }", context);
        Facts facts = new Facts();

        // when
        boolean evaluationResult = condition.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }

    @Test
    public void testSpELConditionWithExpressionAndParserContextAndBeanResolver() {
        StandardEvaluationContext stdContext = new StandardEvaluationContext();
        ExpressionParser parser = new SpelExpressionParser();

        // set beanResolver
        stdContext.setBeanResolver(new SimpleBeanResolver());

        // Invokes resolve(context, "one") on SimpleBeanResolver during evaluation
        Person person = parser.parseExpression("@one").getValue(stdContext, Person.class);
        System.out.println(person);

        // given
        Facts facts = new Facts();
        facts.put("person", person);
        Condition spELCondition = new SpELCondition("#person.age > 18");

        // when
        boolean evaluationResult = spELCondition.evaluate(facts);

        // then
        if (evaluationResult) {
            person.setAdult(true);
        }
        System.out.println(person);

    }
}

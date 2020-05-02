/*
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

import org.assertj.core.api.Assertions;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SpELConditionTest {

    @Test
    public void testSpELExpressionEvaluation() {
        // given
        Condition isAdult = new SpELCondition("#{ ['person'].age > 18 }");
        Facts facts = new Facts();
        facts.put("person", new Person("foo", 20));
        // when
        boolean evaluationResult = isAdult.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }

    // Note this behaviour is different in MVEL, where a missing fact yields an exception
    @Test
    public void whenDeclaredFactIsNotPresent_thenShouldReturnFalse() {
        // given
        Condition isHot = new SpELCondition("#{ ['temperature'] > 30 }");
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

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void testSpELConditionWithExpressionAndParserContextAndBeanResolver() throws Exception {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MySpringAppConfig.class);
        BeanResolver beanResolver = new SimpleBeanResolver(applicationContext);

        SpELRule spELRule = new SpELRule();
        // setting an condition to be evaluated
        spELRule.when("#{ ['person'].age >= 18 }");
        // provided an bean resolver that can resolve "myGreeter"
        spELRule.then("#{ @myGreeter.greeting(#person.name) }", beanResolver);

        // given
        Facts facts = new Facts();
        facts.put("person", new Person("jack", 19));

        // then
        boolean evaluationResult = spELRule.evaluate(facts);
        Assertions.assertThat(evaluationResult).isTrue();

        spELRule.execute(facts);
        Assertions.assertThat(systemOutRule.getLog()).contains("Bonjour jack!");

    }
}

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

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.ExpectedException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SpELActionTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSpELActionExecution() throws Exception {
        // given
        Action markAsAdult = new SpELAction("#{ ['person'].setAdult(true) }");
        Facts facts = new Facts();
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        markAsAdult.execute(facts);

        // then
        assertThat(foo.isAdult()).isTrue();
    }

    @Test
    public void testSpELFunctionExecution() throws Exception {
        // given
        Action printAction = new SpELAction("#{ T(org.jeasy.rules.spel.Person).sayHello() }");
        Facts facts = new Facts();

        // when
        printAction.execute(facts);

        // then
        assertThat(systemOutRule.getLog()).contains("hello");
    }

    @Test
    public void testSpELActionExecutionWithFailure() throws Exception {
        // given
        expectedException.expect(Exception.class);
        expectedException.expectMessage("EL1004E: Method call: Method sayHi() cannot be found on type org.jeasy.rules.spel.Person");
        Action action = new SpELAction("#{ T(org.jeasy.rules.spel.Person).sayHi() }");
        Facts facts = new Facts();
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        action.execute(facts);

        // then
        // excepted exception
    }

    @Test
    public void testSpELActionWithExpressionAndParserContext() throws Exception {
        // given
        ParserContext context = new TemplateParserContext("%{", "}");
        Action printAction = new SpELAction("%{ T(org.jeasy.rules.spel.Person).sayHello() }", context);
        Facts facts = new Facts();

        // when
        printAction.execute(facts);

        // then
        assertThat(systemOutRule.getLog()).contains("hello");

    }
}

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
package org.jeasy.rules.jexl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.UUID;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.introspection.JexlSandbox;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.ExpectedException;

public class JexlActionTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @SuppressWarnings("deprecation")
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testMVELActionExecution() throws Exception {
        // given
        Action markAsAdult = new JexlAction("person.setAdult(true);");
        Facts facts = new Facts();
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        markAsAdult.execute(facts);

        // then
        assertThat(foo.isAdult()).isTrue();
    }

    @Test
    public void testJexlFunctionExecution() throws Exception {
        // given
        Action printAction = new JexlAction("var hello = function() { System.out.println(\"Hello from JEXL!\"); }; hello();");
        Facts facts = new Facts();
        facts.put("System", System.class);

        // when
        printAction.execute(facts);

        // then
        assertThat(systemOutRule.getLog()).contains("Hello from JEXL!");
    }

    @Test
    public void testMVELActionExecutionWithFailure() throws Exception {
        // given
        expectedException.expect(JexlException.Method.class);
        expectedException.expectMessage("org.jeasy.rules.jexl.JexlAction.<init>@1:7 unsolvable function/method 'setBlah'");
        Action action = new JexlAction("person.setBlah(true);");
        Facts facts = new Facts();
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        action.execute(facts);

        // then
        // excepted exception
    }

    @Test
    public void testJexlActionWithExpressionAndFacts() throws Exception {
        // given
        JexlEngine jexl = new JexlBuilder()
                .create();
        Action printAction = new JexlAction(
                "var random = function() { System.out.println(\"Random from JEXL = \" + new('java.util.Random', 123).nextInt(10)); }; random();",
                jexl);
        Facts facts = new Facts();
        facts.put("System", System.class);

        // when
        printAction.execute(facts);

        // then
        assertThat(systemOutRule.getLog()).contains("Random from JEXL = 2");
    }

    @Test
    public void testWithBlackSanbox() throws Exception {
        JexlSandbox sandbox = new JexlSandbox(false);
        sandbox.white(System.class.getName()).execute("currentTimeMillis");
        JexlEngine jexl = new JexlBuilder()
                .sandbox(sandbox)
                .create();

        Facts facts = new Facts();
        facts.put("result", null);
        facts.put("System", System.class);
        facts.put("UUID", UUID.class);

        long now = System.currentTimeMillis();
        new JexlAction("result = System.currentTimeMillis()", jexl).execute(facts);
        Object result = facts.get("result");
        assertThat(result).isInstanceOf(Long.class);
        assertThat((long) result).isGreaterThanOrEqualTo(now);

        try {
            new JexlAction("result = System.nanoTime()", jexl).execute(facts);
            fail("Exception expected");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JexlException.Method.class);
        }

        try {
            new JexlAction("result = new('java.lang.Double', 10)", jexl).execute(facts);
            fail("Exception expected");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JexlException.Method.class);
        }

        try {
            new JexlAction("result = new('java.io.File', '/tmp/jexl-sandbox-test')", jexl).execute(facts);
            fail("Exception expected");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JexlException.Method.class);
        }

        try {
            new JexlAction("result = UUID.randomUUID()", jexl).execute(facts);
            fail("Exception expected");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JexlException.Method.class);
        }

        try {
            new JexlAction("result = Math.PI", jexl).execute(facts);
            fail("Exception expected");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JexlException.Variable.class);
        }
    }

    @Test
    public void testWithWhiteSanbox() throws Exception {
        JexlSandbox sandbox = new JexlSandbox(true);
        sandbox.black(System.class.getName()).execute("nanoTime");
        JexlEngine jexl = new JexlBuilder()
                .sandbox(sandbox)
                .create();

        Facts facts = new Facts();
        facts.put("result", null);
        facts.put("System", System.class);
        facts.put("UUID", UUID.class);

        long now = System.currentTimeMillis();
        new JexlAction("result = System.currentTimeMillis()", jexl).execute(facts);
        Object result = facts.get("result");
        assertThat(result).isInstanceOf(Long.class);
        assertThat((long) result).isGreaterThanOrEqualTo(now);

        try {
            new JexlAction("result = System.nanoTime()", jexl).execute(facts);
            fail("Exception expected");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JexlException.Method.class);
        }

        new JexlAction("result = new('java.lang.Double', 10)", jexl).execute(facts);
        result = facts.get("result");
        assertThat(result).isInstanceOf(Double.class);
        assertThat((double) result).isEqualTo(10.0);

        new JexlAction("result = new('java.io.File', '/tmp/jexl-sandbox-test')", jexl).execute(facts);
        result = facts.get("result");
        assertThat(result).isInstanceOf(File.class);
        assertThat(((File) result).exists()).isFalse();
        assertThat(((File) result).getName()).isEqualTo("jexl-sandbox-test");

        new JexlAction("result = UUID.randomUUID()", jexl).execute(facts);
        result = facts.get("result");
        assertThat(result).isInstanceOf(UUID.class);

        try {
            new JexlAction("result = Math.PI", jexl).execute(facts);
            fail("Exception expected");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JexlException.Variable.class);
        }
    }
}

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
package org.jeasy.rules.jexl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.introspection.JexlSandbox;
import org.assertj.core.api.Assertions;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import org.junit.Test;

/**
 * @author Lauri Kimmel
 * @author Mahmoud Ben Hassine
 */
public class JexlActionTest {


    @Test
    public void testJexlActionExecution() throws Exception {
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
    public void testJexlActionExecutionWithCustomFunction() throws Exception {
        // given
        PrintStream originalStream = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Map<String, Object> namespaces = new HashMap<>();
        namespaces.put("sout", System.out);
        JexlEngine jexlEngine = new JexlBuilder()
                .namespaces(namespaces)
                .create();
        Action printAction = new JexlAction("var hello = function() { sout:println(\"Hello from JEXL!\"); }; hello();", jexlEngine);
        Facts facts = new Facts();

        // when
        printAction.execute(facts);

        // then
        assertThat(outputStream.toString()).startsWith("Hello from JEXL!");
        System.setOut(originalStream);
    }

    @Test
    public void testJexlActionExecutionWithFailure() {
        // given
        Action action = new JexlAction("person.setBlah(true);");
        Facts facts = new Facts();
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        Assertions.assertThatThrownBy(() -> action.execute(facts))
                .isInstanceOf(JexlException.Method.class)
                .hasMessage("org.jeasy.rules.jexl.JexlAction.<init>@1:7 unsolvable function/method 'setBlah'");

        // then
        // excepted exception
    }

    @Test
    public void testJexlActionWithExpressionAndFacts() throws Exception {
        // given
        PrintStream originalStream = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Map<String, Object> namespaces = new HashMap<>();
        namespaces.put("sout", System.out);
        JexlEngine jexlEngine = new JexlBuilder()
                .namespaces(namespaces)
                .create();
        Action printAction = new JexlAction("var random = function() { sout:println(\"Random from JEXL = \" + new('java.util.Random', 123).nextInt(10)); }; random();", jexlEngine);
        Facts facts = new Facts();

        // when
        printAction.execute(facts);

        // then
        assertThat(outputStream.toString()).startsWith("Random from JEXL = 2");
        System.setOut(originalStream);
    }

    @Test
    public void testWithBlackSandbox() {
        // given
        JexlSandbox sandbox = new JexlSandbox(false);
        sandbox.black(System.class.getName()).execute("currentTimeMillis");
        Map<String, Object> namespaces = new HashMap<>();
        namespaces.put("s", System.class);
        JexlEngine jexl = new JexlBuilder()
                .sandbox(sandbox)
                .namespaces(namespaces)
                .create();
        Facts facts = new Facts();

        // when
        JexlAction jexlAction = new JexlAction("s:currentTimeMillis()", jexl);

        // then
        assertThatThrownBy(() -> jexlAction.execute(facts)).isInstanceOf(JexlException.Method.class);
    }

    @Test
    public void testWithWhiteSandbox() {
        // given
        JexlSandbox sandbox = new JexlSandbox(true);
        sandbox.white(System.class.getName()).execute("currentTimeMillis");
        Map<String, Object> namespaces = new HashMap<>();
        namespaces.put("s", System.class);
        JexlEngine jexl = new JexlBuilder()
                .sandbox(sandbox)
                .namespaces(namespaces)
                .create();
        Facts facts = new Facts();
        AtomicLong atomicLong = new AtomicLong();
        facts.put("result", atomicLong);

        long now = System.currentTimeMillis();
        JexlAction jexlAction = new JexlAction("result.set(s:currentTimeMillis())", jexl);

        // when
        jexlAction.execute(facts);
        
        // then
        AtomicLong result = facts.get("result");
        assertThat(result.get()).isGreaterThanOrEqualTo(now);
    }

}

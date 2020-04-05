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
package org.jeasy.rules.api;

import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class FactsTest {

    private Facts facts = new Facts();

    @Test
    public void factsMustHaveUniqueName() throws Exception {
        facts.put("foo", 1);
        facts.put("foo", 2);

        assertThat(facts).hasSize(1);
        Object foo = facts.get("foo");
        assertThat(foo).isEqualTo(2);
    }

    @Test
    public void returnOfPut() {
        Object o1 = facts.put("foo", 1);
        Object o2 = facts.put("foo", 2);

        assertThat(o1).isEqualTo(null);
        assertThat(o2).isEqualTo(1);
    }

    @Test
    public void remove() throws Exception {
        facts.put("foo", 1);
        facts.remove("foo");

        assertThat(facts).isEmpty();
    }

    @Test
    public void returnOfRemove() {
        facts.put("foo", 1);
        Object o1 = facts.remove("foo");
        Object o2 = facts.remove("bar");

        assertThat(o1).isEqualTo(1);
        assertThat(o2).isEqualTo(null);
    }

    @Test
    public void get() throws Exception {
        facts.put("foo", 1);
        Object foo = facts.get("foo");
        assertThat(foo).isEqualTo(1);
    }

    @Test
    public void asMap() {
        Object o = facts.asMap();
        assertThat(o instanceof HashMap).isTrue();
    }

    @Test
    public void testClear() {
        Facts facts = new Facts();
        facts.put("foo", "bar");
        facts.clear();
        assertThat(facts.asMap()).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void whenPutNullFact_thenShouldThrowNullPointerException() throws Exception {
        facts.put(null, "foo");
    }

    @Test(expected = NullPointerException.class)
    public void whenRemoveNullFact_thenShouldThrowNullPointerException() throws Exception {
        facts.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public void whenGetNullFact_thenShouldThrowNullPointerException() throws Exception {
        facts.get(null);
    }
}

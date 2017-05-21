package org.jeasy.rules.api;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FactsTest {

    private Facts facts = new Facts();

    @Test
    public void factsMustHaveUniqueName() throws Exception {
        facts.add("foo", 1);
        facts.add("foo", 2);

        assertThat(facts).hasSize(1);
        assertThat(facts.get("foo")).isEqualTo(2);
    }

    @Test
    public void remove() throws Exception {
        facts.add("foo", 1);
        facts.remove("foo");

        assertThat(facts).isEmpty();
    }

    @Test
    public void get() throws Exception {
        facts.add("foo", 1);
        assertThat(facts.get("foo")).isEqualTo(1);
    }

}
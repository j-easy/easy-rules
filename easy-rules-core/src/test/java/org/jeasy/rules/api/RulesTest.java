package org.jeasy.rules.api;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.core.BasicRule;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RulesTest {

    private Rules rules = new Rules();

    @Test
    public void register() throws Exception {
        rules.register(new DummyRule());

        assertThat(rules).hasSize(1);
    }

    @Test
    public void rulesMustHaveUniqueName() throws Exception {
        Rule r1 = new BasicRule("rule");
        Rule r2 = new BasicRule("rule");
        Set<Rule> ruleSet = new HashSet<>();
        ruleSet.add(r1);
        ruleSet.add(r2);

        rules = new Rules(ruleSet);

        assertThat(rules).hasSize(1);
    }

    @Test
    public void unregister() throws Exception {
        DummyRule rule = new DummyRule();
        rules.register(rule);
        rules.unregister(rule);

        assertThat(rules).isEmpty();
    }

    @Test
    public void isEmpty() throws Exception {
        assertThat(rules.isEmpty()).isTrue();
    }

    @Test
    public void clear() throws Exception {
        rules.register(new DummyRule());
        rules.clear();

        assertThat(rules).isEmpty();
    }

    @org.jeasy.rules.annotation.Rule
    class DummyRule {
        @Condition
        public boolean when() { return true; }

        @Action
        public void then() { }
    }

}
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
 * Null value in facts must be accepted, this is not same thing that fact missing
 */
public class NullFactAnnotationParameterTest extends AbstractTest {

    @Test
    public void testNullFact() {
        Rules rules = new Rules();
        rules.register(new AnnotatedParametersRule());

        Facts facts = new Facts();
        facts.put("fact1", new Object());
        facts.put("fact2", null);

        Map<org.jeasy.rules.api.Rule, Boolean> results = rulesEngine.check(rules, facts);

        for (boolean b : results.values()) {
            Assert.assertTrue(b);
        }
    }

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
    public class AnnotatedParametersRule {

        @Condition
        public boolean when(@Fact("fact1") Object fact1, @Fact("fact2") Object fact2) {
            return fact1 != null && fact2 == null;
        }

        @Action
        public void then(@Fact("fact1") Object fact1, @Fact("fact2") Object fact2) {
        }

    }
}

package org.easyrules.core;

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link org.easyrules.core.DefaultRulesEngine}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class DefaultRulesEngineTest {

    @Test
    public void testRulesWithDifferentNameAndDescriptionButWithSamePriority() throws Exception {

        SimpleRule rule1 = new SimpleRule("rule 1", "description 1", 0);
        SimpleRule rule2 = new SimpleRule("rule 2", "description 2", 0);
        SimpleRule rule3 = new SimpleRule("rule 3", "description 3", 1);

        RulesEngine<Rule> engine = new DefaultRulesEngine();
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        engine.registerRule(rule3);
        engine.fireRules();

        assertThat(rule1.isExecuted()).isTrue();
        assertThat(rule2.isExecuted()).isTrue();
        assertThat(rule3.isExecuted()).isTrue();

    }

    @Test
    public void testRulesWithSameNameAndDescriptionAndPriority() throws Exception {

        SimpleRule rule1 = new SimpleRule("rule 1", "description 1", 0);
        SimpleRule rule2 = new SimpleRule("rule 1", "description 1", 0);

        RulesEngine<Rule> engine = new DefaultRulesEngine();
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        engine.fireRules();

        assertThat(rule1.isExecuted()).isTrue();
        assertThat(rule2.isExecuted()).isFalse();

    }

    class SimpleRule extends BasicRule {

        /**
         * Has the rule been executed? .
         */
        protected boolean executed;

        public SimpleRule(String name, String description) {
            super(name, description);
        }

        public SimpleRule(String name, String description, int priority) {
            super(name, description, priority);
        }

        @Override
        public boolean evaluateConditions() {
            return true;
        }

        @Override
        public void performActions() throws Exception {
            executed = true;
        }

        public boolean isExecuted() {
            return executed;
        }

    }
}

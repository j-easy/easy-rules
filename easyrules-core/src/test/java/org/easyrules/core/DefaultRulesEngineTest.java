package org.easyrules.core;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.easyrules.api.RulesEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link org.easyrules.core.DefaultRulesEngine}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class DefaultRulesEngineTest {

    private SimpleRule simpleRule;

    private SimpleAnnotatedRule simpleAnnotatedRule;

    private RulesEngine rulesEngine;

    @Before
    public void setup() {
        simpleRule = new SimpleRule();
        simpleAnnotatedRule = new SimpleAnnotatedRule();
        rulesEngine = new RulesEngineBuilder().build();
    }

    @Test
    public void whenConditionIsTrue_thenActionShouldBeExecuted() {
        rulesEngine.registerRule(simpleAnnotatedRule);
        rulesEngine.fireRules();
        assertThat(simpleAnnotatedRule.isExecuted()).isTrue();

    }

    @Test
    public void actionsMustBeExecutedInTheDefinedOrder() {
        rulesEngine.registerRule(simpleAnnotatedRule);
        rulesEngine.fireRules();
        assertEquals("012", simpleAnnotatedRule.getActionSequence());
    }

    @Test
    public void testRulesWithDifferentNameAndDescriptionButWithSamePriority() throws Exception {

        SimpleRule rule1 = new SimpleRule("rule 1", "description 1", 0);
        SimpleRule rule2 = new SimpleRule("rule 2", "description 2", 0);
        SimpleRule rule3 = new SimpleRule("rule 3", "description 3", 1);

        rulesEngine.registerRule(rule1);
        rulesEngine.registerRule(rule2);
        rulesEngine.registerRule(rule3);
        rulesEngine.fireRules();

        assertThat(rule1.isExecuted()).isTrue();
        assertThat(rule2.isExecuted()).isTrue();
        assertThat(rule3.isExecuted()).isTrue();

    }

    @Test
    public void testRulesWithSameNameAndDescriptionAndPriority() throws Exception {

        SimpleRule rule1 = new SimpleRule("rule 1", "description 1", 0);
        SimpleRule rule2 = new SimpleRule("rule 1", "description 1", 0);

        RulesEngine engine = new RulesEngineBuilder().build();
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        engine.fireRules();

        assertThat(rule1.isExecuted()).isTrue();
        assertThat(rule2.isExecuted()).isFalse();

    }

    @After
    public void clearRules() {
        rulesEngine.clearRules();
    }

    class SimpleRule extends BasicRule {

        /**
         * Has the rule been executed? .
         */
        protected boolean executed;

        public SimpleRule() {
        }

        public SimpleRule(String name) {
            super(name);
        }

        public SimpleRule(String name, String description) {
            super(name, description);
        }

        public SimpleRule(String name, String description, int priority) {
            super(name, description, priority);
        }

        @Override
        public boolean evaluate() {
            return true;
        }

        @Override
        public void execute() throws Exception {
            executed = true;
        }

        public boolean isExecuted() {
            return executed;
        }

    }

    @Rule(name = "myRule", description = "my rule description")
    public class SimpleAnnotatedRule {

        private boolean executed;

        private String actionSequence = "";

        @Condition
        public boolean when() {
            return condition1() && condition2() || condition3();
        }

        private boolean condition1() {
            return true;
        }

        private boolean condition2() {
            return false;
        }

        private boolean condition3() {
            return true;
        }

        @Action
        public void then0() throws Exception {
            actionSequence += "0";
        }

        @Action(order = 1)
        public void then1() throws Exception {
            actionSequence += "1";
        }

        @Action(order = 2)
        public void then2() throws Exception {
            actionSequence += "2";
            executed = true;
        }

        public boolean isExecuted() {
            return executed;
        }

        public String getActionSequence() {
            return actionSequence;
        }

    }
}

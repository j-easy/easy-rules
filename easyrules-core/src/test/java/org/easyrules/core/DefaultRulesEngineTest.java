package org.easyrules.core;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;
import org.easyrules.api.RulesEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link org.easyrules.core.DefaultRulesEngine}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultRulesEngineTest {

    @Mock
    private BasicRule rule, anotherRule;

    private AnnotatedRule annotatedRule;

    private RulesEngine rulesEngine;

    @Before
    public void setup() {
        when(rule.getName()).thenReturn("r");
        when(rule.getDescription()).thenReturn("d");
        when(rule.getPriority()).thenReturn(1);
        annotatedRule = new AnnotatedRule();
        rulesEngine = aNewRulesEngine().build();
    }

    @Test
    public void whenConditionIsTrue_thenActionShouldBeExecuted() throws Exception {
        when(rule.evaluate()).thenReturn(true);
        rulesEngine.registerRule(rule);

        rulesEngine.fireRules();

        verify(rule).execute();
    }

    @Test
    public void whenConditionIsFalse_thenActionShouldNotBeExecuted() throws Exception {
        when(rule.evaluate()).thenReturn(false);
        rulesEngine.registerRule(rule);

        rulesEngine.fireRules();

        verify(rule, never()).execute();
    }

    @Test
    public void rulesMustBeTriggeredInTheirNaturalOrder() throws Exception {
        when(rule.evaluate()).thenReturn(true);
        when(anotherRule.evaluate()).thenReturn(true);
        when(rule.compareTo(anotherRule)).thenReturn(-1);
        when(anotherRule.compareTo(rule)).thenReturn(1);
        rulesEngine.registerRule(rule);
        rulesEngine.registerRule(anotherRule);

        rulesEngine.fireRules();

        InOrder inOrder = inOrder(rule, anotherRule);
        inOrder.verify(rule).execute();
        inOrder.verify(anotherRule).execute();
    }

    @Test
    public void actionsMustBeExecutedInTheDefinedOrder() {
        rulesEngine.registerRule(annotatedRule);
        rulesEngine.fireRules();
        assertEquals("012", annotatedRule.getActionSequence());
    }

    @Test
    public void annotatedRulesAndNonAnnotatedRulesShouldBeUsableTogether() throws Exception {
        when(rule.evaluate()).thenReturn(true);
        rulesEngine.registerRule(rule);
        rulesEngine.registerRule(annotatedRule);

        rulesEngine.fireRules();

        verify(rule).execute();
        assertThat(annotatedRule.isExecuted()).isTrue();
    }

    @After
    public void clearRules() {
        rulesEngine.clearRules();
    }

    @Rule(name = "myRule", description = "my rule description")
    public class AnnotatedRule {

        private boolean executed;

        private String actionSequence = "";

        @Condition
        public boolean when() {
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

        @Priority
        public int getPriority() {
            return 0;
        }

        public boolean isExecuted() {
            return executed;
        }

        public String getActionSequence() {
            return actionSequence;
        }

    }
}

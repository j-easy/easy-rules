package org.easyrules.core;

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

/**
 * Test class for custom rule ordering.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomRuleOrderingTest {

    @Mock
    private MyRule rule1, rule2;

    private RulesEngine rulesEngine;

    @Before
    public void setup() {
        rulesEngine = aNewRulesEngine().build();
    }

    @Test
    public void whenCompareToIsOverridden_thenShouldExecuteRulesInTheCustomOrder() throws Exception {

        when(rule1.getName()).thenReturn("a");
        when(rule1.getPriority()).thenReturn(1);
        when(rule1.evaluate()).thenReturn(true);

        when(rule2.getName()).thenReturn("b");
        when(rule2.getPriority()).thenReturn(0);
        when(rule2.evaluate()).thenReturn(true);

        when(rule2.compareTo(rule1)).thenCallRealMethod();

        rulesEngine.registerRule(rule1);
        rulesEngine.registerRule(rule2);

        rulesEngine.fireRules();

        /*
         * By default, if compareTo is not overridden, then rule2 should be executed first (priority 0 < 1).
         * But in this case, the compareTo method order rules by their name, so rule1 should be
         * executed first ("a" < "b")
         */
        InOrder inOrder = inOrder(rule1, rule2);
        inOrder.verify(rule1).execute();
        inOrder.verify(rule2).execute();

    }

    @After
    public void clearRules() {
        rulesEngine.clearRules();
    }

    class MyRule extends BasicRule {

        @Override
        public int compareTo(Rule rule) {
            return getName().compareTo(rule.getName());
        }

    }
}

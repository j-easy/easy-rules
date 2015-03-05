package org.easyrules.core.test.parameters;

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.DefaultRulesEngine;
import org.easyrules.core.test.SimpleRule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        assertTrue(rule1.isExecuted());
        assertTrue(rule2.isExecuted());
        assertTrue(rule3.isExecuted());

    }

    @Test
    public void testRulesWithSameNameAndDescriptionAndPriority() throws Exception {

        SimpleRule rule1 = new SimpleRule("rule 1", "description 1", 0);
        SimpleRule rule2 = new SimpleRule("rule 1", "description 1", 0);

        RulesEngine<Rule> engine = new DefaultRulesEngine();
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        engine.fireRules();

        assertTrue(rule1.isExecuted());
        assertFalse(rule2.isExecuted());

    }
}

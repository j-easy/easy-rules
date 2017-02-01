package org.easyrules.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.easyrules.api.RuleListener;
import org.easyrules.api.RulesEngine;
import org.junit.Test;

/**
 * Test class for {@link org.easyrules.core.RulesEngineBuilder}.
 *
 * @author Pierre Frisch (pierre.frisch@spearway.com)
 */
public class RulesEngineBuilderTest {
    public static class MyRulesEngine extends DefaultRulesEngine {

        MyRulesEngine(RulesEngineParameters parameters, List<RuleListener> ruleListeners) {
            super(parameters, ruleListeners);
        }

    }

    @Test
    public void checkLegacyRulesEngineBuilderSyntax() throws Exception {
        RulesEngine engine = RulesEngineBuilder.aNewRulesEngine().build();
        assertNotNull("Created legacy rule engine", engine);
        assertTrue("Created parametrized rule engine of class DefaultRulesEngine", engine instanceof DefaultRulesEngine);
    }

    @Test
    public void checkParametrisedRulesEngineBuilderSyntax() throws Exception {
        RulesEngine engine = RulesEngineBuilder.aNewRulesEngine(MyRulesEngine.class).build();
        assertNotNull("Created parametrized rule engine", engine);
        assertTrue("Created parametrized rule engine of class MyRulesEngine", engine instanceof MyRulesEngine);
    }

}

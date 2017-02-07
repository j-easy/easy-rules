package org.easyrules.core;

import org.easyrules.api.RuleListener;
import org.easyrules.api.RulesEngine;
import org.easyrules.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RulesEngineBuilderTest {

    @Mock
    private RuleListener ruleListener;

    @Test
    public void testCreationWithDefaultParameters() {
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();

        assertThat(rulesEngine).isNotNull();
        RulesEngineParameters parameters = rulesEngine.getParameters();

        assertThat(parameters.getName()).isEqualTo(Utils.DEFAULT_ENGINE_NAME);
        assertThat(parameters.getPriorityThreshold()).isEqualTo(Utils.DEFAULT_RULE_PRIORITY_THRESHOLD);

        assertThat(parameters.isSkipOnFirstAppliedRule()).isFalse();
        assertThat(parameters.isSkipOnFirstFailedRule()).isFalse();
        assertThat(parameters.isSkipOnFirstNonTriggeredRule()).isFalse();
    }

    @Test
    public void testCreationWithCustomParameters() {
        String name = "myRulesEngine";
        int expectedThreshold = 10;

        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .named(name)
                .withRuleListener(ruleListener)
                .withRulePriorityThreshold(expectedThreshold)
                .withSilentMode(true)
                .withSkipOnFirstNonTriggeredRule(true)
                .withSkipOnFirstAppliedRule(true)
                .withSkipOnFirstFailedRule(true)
                .build();

        assertThat(rulesEngine).isNotNull();
        RulesEngineParameters parameters = rulesEngine.getParameters();

        assertThat(parameters.getName()).isEqualTo(name);
        assertThat(parameters.getPriorityThreshold()).isEqualTo(expectedThreshold);
        assertThat(parameters.isSilentMode()).isTrue();
        assertThat(parameters.isSkipOnFirstAppliedRule()).isTrue();
        assertThat(parameters.isSkipOnFirstFailedRule()).isTrue();
        assertThat(parameters.isSkipOnFirstNonTriggeredRule()).isTrue();
    }
}
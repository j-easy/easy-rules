/**
 * The MIT License
 *
 *  Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.jeasy.rules.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.RulesEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RulesEngineBuilderTest {

    @Mock
    private RuleListener ruleListener;

    @Test
    public void testCreationWithDefaultParameters() {
        // When
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();

        // Then
        assertThat(rulesEngine).isNotNull();
        RulesEngineParameters parameters = rulesEngine.getParameters();

        assertThat(parameters.getPriorityThreshold()).isEqualTo(RulesEngineParameters.DEFAULT_RULE_PRIORITY_THRESHOLD);

        assertThat(parameters.isSkipOnFirstAppliedRule()).isFalse();
        assertThat(parameters.isSkipOnFirstFailedRule()).isFalse();
        assertThat(parameters.isSkipOnFirstNonTriggeredRule()).isFalse();
    }

    @Test
    public void testCreationWithCustomParameters() {
        // Given
        String name = "myRulesEngine";
        int expectedThreshold = 10;

        // When
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .withRuleListener(ruleListener)
                .withRulePriorityThreshold(expectedThreshold)
                .withSkipOnFirstNonTriggeredRule(true)
                .withSkipOnFirstAppliedRule(true)
                .withSkipOnFirstFailedRule(true)
                .build();

        // Then
        assertThat(rulesEngine).isNotNull();
        RulesEngineParameters parameters = rulesEngine.getParameters();
        assertThat(parameters.getPriorityThreshold()).isEqualTo(expectedThreshold);
        assertThat(parameters.isSkipOnFirstAppliedRule()).isTrue();
        assertThat(parameters.isSkipOnFirstFailedRule()).isTrue();
        assertThat(parameters.isSkipOnFirstNonTriggeredRule()).isTrue();
        assertThat(rulesEngine.getRuleListeners()).hasSize(2).contains(ruleListener);
    }
}

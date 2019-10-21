/**
 * The MIT License
 *
 *  Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.support;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExecutableRuleGroupTest {

    @Mock
    private Rule ruleTrue, ruleFalse, ruleTrueToo;

    private Facts facts = new Facts();
    private Rules rules = new Rules();

    private DefaultRulesEngine rulesEngine = new DefaultRulesEngine();

    private ExecutableRuleGroup executableRuleGroup;

    @Before
    public void before() {
        when(ruleTrue.evaluate(facts)).thenReturn(true);
        when(ruleTrueToo.evaluate(facts)).thenReturn(true);
        when(ruleFalse.evaluate(facts)).thenReturn(false);
        when(ruleFalse.compareTo(ruleTrue)).thenReturn(1);
        when(ruleTrueToo.compareTo(ruleTrue)).thenReturn(1);
    }

    @Test
    public void whenOneRuleIsTrue_thenActionableRuleGroupShouldEvaluateToFalse() {
        // given
        executableRuleGroup = new ExecutableRuleGroup(new Action() {
            @Override
            public void execute(Facts facts) {
                fail("When one rule is true, then ActionableRuleGroup should not execute.");
            }
        });
        executableRuleGroup.addRule(ruleTrue);
        executableRuleGroup.addRule(ruleFalse);

        // when
        boolean evaluationResult = executableRuleGroup.evaluate(facts);

        // then
        assertThat(evaluationResult).isFalse();
    }

    @Test
    public void whenOneRuleIsTrue_thenActionableRuleGroupShouldNotExecute() {
        // given
        executableRuleGroup = new ExecutableRuleGroup(new Action() {
            @Override
            public void execute(Facts facts) {
                fail("When one rule is true, then ActionableRuleGroup should not execute.");
            }
        });
        executableRuleGroup.addRule(ruleTrue);
        executableRuleGroup.addRule(ruleFalse);
        rules.register(executableRuleGroup);

        // when
        rulesEngine.fire(rules, facts);

        // then
        // success!
    }

    @Test
    public void whenBothRulesAreTrue_thenActionableRuleGroupShouldExecute() {
        // given
        final AtomicBoolean evaluateExecution = new AtomicBoolean(false);
        executableRuleGroup = new ExecutableRuleGroup(new Action() {
            @Override
            public void execute(Facts facts) {
                evaluateExecution.set(true);
            }
        });
        executableRuleGroup.addRule(ruleTrue);
        executableRuleGroup.addRule(ruleTrueToo);
        rules.register(executableRuleGroup);

        // when
        rulesEngine.fire(rules, facts);

        // then
        assertThat(evaluateExecution.get()).isTrue();
    }

    @Test
    public void compositeRuleAndComposingRulesMustBeExecuted() throws Exception {
        // given
        final AtomicBoolean evaluateExecution = new AtomicBoolean(false);
        executableRuleGroup = new ExecutableRuleGroup(new Action() {
            @Override
            public void execute(Facts facts) {
                evaluateExecution.set(true);
            }
        });
        executableRuleGroup.addRule(ruleTrue);
        executableRuleGroup.addRule(ruleTrueToo);
        rules.register(executableRuleGroup);

        // when
        rulesEngine.fire(rules, facts);

        // then
        assertThat(evaluateExecution.get()).isTrue();
        verify(ruleTrueToo).execute(facts);
        verify(ruleTrue).execute(facts);
    }
}

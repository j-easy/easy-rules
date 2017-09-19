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

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FactInjectionTest {

    @Test
    public void declaredFactsShouldBeCorrectlyInjectedByNameOrType() throws Exception {
        // Given
        final Object fact1 = new Object();
        final Object fact2 = new Object();
        final Facts facts = new Facts();
        facts.put("fact1", fact1);
        facts.put("fact2", fact2);

        final DummyRule rule = new DummyRule();
        final Rules rules = new Rules(rule);

        // When
        final RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(rule.getFact1()).isSameAs(fact1);
        assertThat(rule.getFact2()).isSameAs(fact2);
        assertThat(rule.getFacts()).isSameAs(facts);
    }

    @Test
    public void rulesShouldBeExecutedWhenFactsAreCorrectlyInjected() throws Exception {
        // Given
        final Facts facts = new Facts();
        facts.put("rain", true);
        facts.put("age", 18);

        final WeatherRule weatherRule = new WeatherRule();
        final AgeRule ageRule = new AgeRule();
        final Rules rules = new Rules(weatherRule, ageRule);

        // When
        final RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(ageRule.isExecuted()).isTrue();
        assertThat(weatherRule.isExecuted()).isTrue();
    }

    @Test
    public void whenFactTypeDoesNotMatchParameterType_thenShouldFireRules() {
        // Given
        final Facts facts = new Facts();
        facts.put("rain", true);
        facts.put("age", "foo");
        final WeatherRule weatherRule = new WeatherRule();
        final AgeRule ageRule = new AgeRule();
        final Rules rules = new Rules(weatherRule, ageRule);
        final RulesEngine rulesEngine = new DefaultRulesEngine();

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(ageRule.isExecuted()).isFalse();
        assertThat(weatherRule.isExecuted()).isTrue();
    }

    @Test
    public void whenFactTypeDoesNotMatchParameterType_thenShouldNotFireNextRules() {
        // Given
        final Facts facts = new Facts();
        facts.put("rain", true);
        facts.put("age", "foo");
        final WeatherRule weatherRule = new WeatherRule();
        final AgeRule ageRule = new AgeRule();
        final Rules rules = new Rules(weatherRule, ageRule);
        final RulesEngineParameters parameters = new RulesEngineParameters();
        parameters.setSkipOnFirstFailedRule(true);
        final RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(ageRule.isExecuted()).isFalse();
        assertThat(weatherRule.isExecuted()).isFalse();
    }

    @Rule
    class DummyRule {

        private Object fact1, fact2;
        private Facts facts;

        @Condition
        public boolean when(@Fact("fact1") final Object fact1, @Fact("fact2") final Object fact2) {
            this.fact1 = fact1;
            this.fact2 = fact2;
            return true;
        }

        @Action
        public void then(final Facts facts) {
            this.facts = facts;
        }

        public Object getFact1() {
            return fact1;
        }

        public Object getFact2() {
            return fact2;
        }

        public Facts getFacts() {
            return facts;
        }
    }

    @Rule
    class AgeRule {

        private boolean isExecuted;

        @Condition
        public boolean isAdult(@Fact("age") final int age) {
            return age >= 18;
        }

        @Action
        public void printYourAreAdult() {
            System.out.println("You are an adult");
            isExecuted = true;
        }

        public boolean isExecuted() {
            return isExecuted;
        }
    }

    @Rule
    class WeatherRule {

        private boolean isExecuted;

        @Condition
        public boolean itRains(@Fact("rain") final boolean rain) {
            return rain;
        }

        @Action
        public void takeAnUmbrella(final Facts facts) {
            System.out.println("It rains, take an umbrella!");
            isExecuted = true;
        }

        public boolean isExecuted() {
            return isExecuted;
        }
    }
}

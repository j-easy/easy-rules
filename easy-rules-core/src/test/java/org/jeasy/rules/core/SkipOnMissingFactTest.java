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
import org.junit.Before;
import org.junit.Test;

/**
 * Test class of "skip on missing fact" parameter of Easy Rules default engine.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class SkipOnMissingFactTest extends AbstractTest {

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void whenSkipOnMissingFact_thenRuleShouldBeSkipped() throws Exception {
        // Given
        rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .withSkipOnMissingFact(true)
                .build();
        final Facts facts = new Facts();
        //facts.put("rain", true);
        final WeatherRule weatherRule = new WeatherRule();
        final Rules rules = new Rules(weatherRule);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(weatherRule.isExecuted()).isFalse();
    }

    @Test
    public void whenSkipOnMissingFactIsNotActivated_thenRuleShouldBeSkipped() throws Exception {
        // Given
        final Facts facts = new Facts();
        //facts.put("rain", true);
        final WeatherRule weatherRule = new WeatherRule();
        final Rules rules = new Rules(weatherRule);

        // When
        rulesEngine.fire(rules, facts);

        // Then
        assertThat(weatherRule.isExecuted()).isFalse();
    }

    @Rule(name = "weather rule", description = "if it rains then take an umbrella" )
    public class WeatherRule {

        private boolean executed = false;

        @Condition
        public boolean itRains(@Fact("rain") final boolean rain) {
            return rain;
        }

        @Action
        public void takeAnUmbrella() {
            System.out.println("It rains, take an umbrella!");
            executed = true;
        }

        public boolean isExecuted() {
            return executed;
        }
    }

}

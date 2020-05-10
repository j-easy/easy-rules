/*
 * The MIT License
 *
 *  Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.support.reader;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.support.RuleDefinition;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO use parametrized test to merge this test class with JsonRuleDefinitionReaderTest
public class YamlRuleDefinitionReaderTest {

    private RuleDefinitionReader ruleDefinitionReader = new YamlRuleDefinitionReader();

    @Test
    public void testRuleDefinitionReadingFromFile() throws Exception {
        // given
        File adultRuleDescriptor = new File("src/test/resources/adult-rule.yml");

        // when
        List<RuleDefinition> ruleDefinitions = ruleDefinitionReader.read(new FileReader(adultRuleDescriptor));

        // then
        assertThat(ruleDefinitions).hasSize(1);
        RuleDefinition adultRuleDefinition = ruleDefinitions.get(0);
        assertThat(adultRuleDefinition).isNotNull();
        assertThat(adultRuleDefinition.getName()).isEqualTo("adult rule");
        assertThat(adultRuleDefinition.getDescription()).isEqualTo("when age is greater than 18, then mark as adult");
        assertThat(adultRuleDefinition.getPriority()).isEqualTo(1);
        assertThat(adultRuleDefinition.getCondition()).isEqualTo("person.age > 18");
        assertThat(adultRuleDefinition.getActions()).isEqualTo(Collections.singletonList("person.setAdult(true);"));
    }

    @Test
    public void testRuleDefinitionReadingFromString() throws Exception {
        // given
        String adultRuleDescriptor = new String(Files.readAllBytes(Paths.get("src/test/resources/adult-rule.yml")));

        // when
        List<RuleDefinition> ruleDefinitions = ruleDefinitionReader.read(new StringReader(adultRuleDescriptor));

        // then
        assertThat(ruleDefinitions).hasSize(1);
        RuleDefinition adultRuleDefinition = ruleDefinitions.get(0);
        assertThat(adultRuleDefinition).isNotNull();
        assertThat(adultRuleDefinition.getName()).isEqualTo("adult rule");
        assertThat(adultRuleDefinition.getDescription()).isEqualTo("when age is greater than 18, then mark as adult");
        assertThat(adultRuleDefinition.getPriority()).isEqualTo(1);
        assertThat(adultRuleDefinition.getCondition()).isEqualTo("person.age > 18");
        assertThat(adultRuleDefinition.getActions()).isEqualTo(Collections.singletonList("person.setAdult(true);"));
    }

    @Test
    public void testRuleDefinitionReading_withDefaultValues() throws Exception {
        // given
        File adultRuleDescriptor = new File("src/test/resources/adult-rule-with-default-values.yml");

        // when
        List<RuleDefinition> ruleDefinitions = ruleDefinitionReader.read(new FileReader(adultRuleDescriptor));

        // then
        assertThat(ruleDefinitions).hasSize(1);
        RuleDefinition adultRuleDefinition = ruleDefinitions.get(0);
        assertThat(adultRuleDefinition).isNotNull();
        assertThat(adultRuleDefinition.getName()).isEqualTo(Rule.DEFAULT_NAME);
        assertThat(adultRuleDefinition.getDescription()).isEqualTo(Rule.DEFAULT_DESCRIPTION);
        assertThat(adultRuleDefinition.getPriority()).isEqualTo(Rule.DEFAULT_PRIORITY);
        assertThat(adultRuleDefinition.getCondition()).isEqualTo("person.age > 18");
        assertThat(adultRuleDefinition.getActions()).isEqualTo(Collections.singletonList("person.setAdult(true);"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRuleDefinitionReading_whenNoCondition() throws Exception {
        // given
        File adultRuleDescriptor = new File("src/test/resources/adult-rule-without-condition.yml");

        // when
        List<RuleDefinition> ruleDefinitions = ruleDefinitionReader.read(new FileReader(adultRuleDescriptor));

        // then
        // expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRuleDefinitionReading_whenNoActions() throws Exception {
        // given
        File adultRuleDescriptor = new File("src/test/resources/adult-rule-without-actions.yml");

        // when
        List<RuleDefinition> ruleDefinitions = ruleDefinitionReader.read(new FileReader(adultRuleDescriptor));

        // then
        // expected exception
    }

    @Test
    public void testRulesDefinitionReading() throws Exception {
        // given
        File rulesDescriptor = new File("src/test/resources/rules.yml");

        // when
        List<RuleDefinition> ruleDefinitions = ruleDefinitionReader.read(new FileReader(rulesDescriptor));

        // then
        assertThat(ruleDefinitions).hasSize(2);
        RuleDefinition ruleDefinition = ruleDefinitions.get(0);
        assertThat(ruleDefinition).isNotNull();
        assertThat(ruleDefinition.getName()).isEqualTo("adult rule");
        assertThat(ruleDefinition.getDescription()).isEqualTo("when age is greater than 18, then mark as adult");
        assertThat(ruleDefinition.getPriority()).isEqualTo(1);
        assertThat(ruleDefinition.getCondition()).isEqualTo("person.age > 18");
        assertThat(ruleDefinition.getActions()).isEqualTo(Collections.singletonList("person.setAdult(true);"));

        ruleDefinition = ruleDefinitions.get(1);
        assertThat(ruleDefinition).isNotNull();
        assertThat(ruleDefinition.getName()).isEqualTo("weather rule");
        assertThat(ruleDefinition.getDescription()).isEqualTo("when it rains, then take an umbrella");
        assertThat(ruleDefinition.getPriority()).isEqualTo(2);
        assertThat(ruleDefinition.getCondition()).isEqualTo("rain == true");
        assertThat(ruleDefinition.getActions()).isEqualTo(Collections.singletonList("System.out.println(\"It rains, take an umbrella!\");"));
    }

    @Test
    public void testEmptyRulesDefinitionReading() throws Exception {
        // given
        File rulesDescriptor = new File("src/test/resources/rules-empty.yml");

        // when
        List<RuleDefinition> ruleDefinitions = ruleDefinitionReader.read(new FileReader(rulesDescriptor));

        // then
        assertThat(ruleDefinitions).hasSize(0);
    }

    @Test
    public void testRuleDefinitionReading_withCompositeAndBasicRules() throws Exception {
        // given
        File compositeRuleDescriptor = new File("src/test/resources/composite-rules.yml");

        // when
        List<RuleDefinition> ruleDefinitions = ruleDefinitionReader.read(new FileReader(compositeRuleDescriptor));

        // then
        assertThat(ruleDefinitions).hasSize(2);

        // then
        RuleDefinition ruleDefinition = ruleDefinitions.get(0);
        assertThat(ruleDefinition).isNotNull();
        assertThat(ruleDefinition.getName()).isEqualTo("Movie id rule");
        assertThat(ruleDefinition.getDescription()).isEqualTo("description");
        assertThat(ruleDefinition.getPriority()).isEqualTo(1);
        assertThat(ruleDefinition.getCompositeRuleType()).isEqualTo("UnitRuleGroup");
        assertThat(ruleDefinition.getComposingRules()).isNotEmpty();

        List<RuleDefinition> subrules = ruleDefinition.getComposingRules();
        assertThat(subrules).hasSize(2);

        RuleDefinition subrule = subrules.get(0);
        assertThat(subrule.getName()).isEqualTo("Time is evening");
        assertThat(subrule.getDescription()).isEqualTo("If it's later than 7pm");
        assertThat(subrule.getPriority()).isEqualTo(1);

        subrule = subrules.get(1);
        assertThat(subrule.getName()).isEqualTo("Movie is rated R");
        assertThat(subrule.getDescription()).isEqualTo("If the movie is rated R");
        assertThat(subrule.getPriority()).isEqualTo(1);

        ruleDefinition = ruleDefinitions.get(1);
        assertThat(ruleDefinition).isNotNull();
        assertThat(ruleDefinition.getName()).isEqualTo("weather rule");
        assertThat(ruleDefinition.getDescription()).isEqualTo("when it rains, then take an umbrella");
        assertThat(ruleDefinition.getComposingRules()).isEmpty();
        assertThat(ruleDefinition.getCondition()).isEqualTo("rain == True");
        assertThat(ruleDefinition.getActions()).isEqualTo(Collections.singletonList("System.out.println(\"It rains, take an umbrella!\");"));
    }
}

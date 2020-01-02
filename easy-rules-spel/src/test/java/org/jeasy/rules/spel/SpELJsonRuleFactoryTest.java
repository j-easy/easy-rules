/**
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
package org.jeasy.rules.spel;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.support.JsonRuleDefinitionReader;
import org.jeasy.rules.support.UnitRuleGroup;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

// TODO use parametrized test to merge this test class with SpELYamlRuleFactoryTest
public class SpELJsonRuleFactoryTest {

    @org.junit.Rule
    public ExpectedException expectedException = ExpectedException.none();

    private SpELRuleFactory factory = new SpELRuleFactory(new JsonRuleDefinitionReader());

    @Test
    public void testRulesCreation() throws Exception {
        // given
        File rulesDescriptor = new File("src/test/resources/rules.json");

        // when
        Rules rules = factory.createRules(new FileReader(rulesDescriptor));

        // then
        assertThat(rules).hasSize(2);
        Iterator<Rule> iterator = rules.iterator();

        Rule rule = iterator.next();
        assertThat(rule).isNotNull();
        assertThat(rule.getName()).isEqualTo("adult rule");
        assertThat(rule.getDescription()).isEqualTo("when age is greater then 18, then mark as adult");
        assertThat(rule.getPriority()).isEqualTo(1);

        rule = iterator.next();
        assertThat(rule).isNotNull();
        assertThat(rule.getName()).isEqualTo("weather rule");
        assertThat(rule.getDescription()).isEqualTo("when it rains, then take an umbrella");
        assertThat(rule.getPriority()).isEqualTo(2);
    }

    @Test
    public void testRuleCreationFromFileReader() throws Exception{
        // given
        Reader adultRuleDescriptorAsReader = new FileReader("src/test/resources/adult-rule.json");

        // when
        Rule adultRule = factory.createRule(adultRuleDescriptorAsReader);

        // then
        assertThat(adultRule.getName()).isEqualTo("adult rule");
        assertThat(adultRule.getDescription()).isEqualTo("when age is greater then 18, then mark as adult");
        assertThat(adultRule.getPriority()).isEqualTo(1);
    }

    @Test
    public void testRuleCreationFromStringReader() throws Exception{
        // given
        Reader adultRuleDescriptorAsReader = new StringReader(new String(Files.readAllBytes(Paths.get("src/test/resources/adult-rule.json"))));

        // when
        Rule adultRule = factory.createRule(adultRuleDescriptorAsReader);

        // then
        assertThat(adultRule.getName()).isEqualTo("adult rule");
        assertThat(adultRule.getDescription()).isEqualTo("when age is greater then 18, then mark as adult");
        assertThat(adultRule.getPriority()).isEqualTo(1);
    }

    @Test
    public void testRuleCreationFromFileReader_withCompositeRules() throws Exception {
        // given
        File rulesDescriptor = new File("src/test/resources/composite-rules.json");

        // when
        Rules rules = factory.createRules(new FileReader(rulesDescriptor));

        // then
        assertThat(rules).hasSize(2);
        Iterator<Rule> iterator = rules.iterator();

        Rule rule = iterator.next();
        assertThat(rule).isNotNull();
        assertThat(rule.getName()).isEqualTo("Movie id rule");
        assertThat(rule.getDescription()).isEqualTo("description");
        assertThat(rule.getPriority()).isEqualTo(1);
        assertThat(rule).isInstanceOf(UnitRuleGroup.class);

        rule = iterator.next();
        assertThat(rule).isNotNull();
        assertThat(rule.getName()).isEqualTo("weather rule");
        assertThat(rule.getDescription()).isEqualTo("when it rains, then take an umbrella");
        assertThat(rule.getPriority()).isEqualTo(1);;
    }

    @Test
    public void testRuleCreationFromFileReader_withInvalidCompositeRuleType() throws Exception {
        // given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid composite rule type, must be one of [UnitRuleGroup, ConditionalRuleGroup, ActivationRuleGroup]");
        File rulesDescriptor = new File("src/test/resources/composite-rule-invalid-composite-rule-type.json");

        // when
        Rule rule = factory.createRule(new FileReader(rulesDescriptor));

        // then
        // expected exception
    }

    @Test
    public void testRuleCreationFromFileReader_withEmptyComposingRules() throws Exception {
        // given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Composite rules must have composing rules specified");
        File rulesDescriptor = new File("src/test/resources/composite-rule-invalid-empty-composing-rules.json");

        // when
        Rule rule = factory.createRule(new FileReader(rulesDescriptor));

        // then
        // expected exception
    }

    @Test
    public void testRuleCreationFromFileReader_withNonCompositeRuleDeclaresComposingRules() throws Exception {
        // given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Non-composite rules cannot have composing rules");
        File rulesDescriptor = new File("src/test/resources/non-composite-rule-with-composing-rules.json");

        // when
        Rule rule = factory.createRule(new FileReader(rulesDescriptor));

        // then
        // expected exception
    }
}

package org.jeasy.rules.mvel;

import org.junit.Test;

import java.io.File;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELRuleDefinitionReaderTest {

    private MVELRuleDefinitionReader ruleDefinitionReader = new MVELRuleDefinitionReader();

    @Test
    public void testRuleDefinitionReading() throws Exception {
        // given
        File adultRuleDescriptor = new File("src/test/resources/adult-rule.yml");

        // when
        MVELRuleDefinition adultRuleDefinition = ruleDefinitionReader.read(adultRuleDescriptor);

        // then
        assertThat(adultRuleDefinition).isNotNull();
        assertThat(adultRuleDefinition.getName()).isEqualTo("adult rule");
        assertThat(adultRuleDefinition.getDescription()).isEqualTo("when age is greater then 18, then mark as adult");
        assertThat(adultRuleDefinition.getPriority()).isEqualTo(1);
        assertThat(adultRuleDefinition.getCondition()).isEqualTo("person.age > 18");
        assertThat(adultRuleDefinition.getActions()).isEqualTo(Collections.singletonList("person.setAdult(true);"));
    }

}
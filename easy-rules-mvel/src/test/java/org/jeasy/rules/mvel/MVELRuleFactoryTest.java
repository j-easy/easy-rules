package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Rule;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELRuleFactoryTest {

    @Test
    public void testRuleCreation() throws Exception {
        // given
        File adultRuleDescriptor = new File("src/test/resources/adult-rule.yml");

        // when
        Rule adultRule = MVELRuleFactory.createRuleFrom(adultRuleDescriptor);

        // then
        assertThat(adultRule).isInstanceOf(MVELRule.class);
        assertThat(adultRule.getName()).isEqualTo("adult rule");
        assertThat(adultRule.getDescription()).isEqualTo("when age is greater then 18, then mark as adult");
        assertThat(adultRule.getPriority()).isEqualTo(1);
    }
}
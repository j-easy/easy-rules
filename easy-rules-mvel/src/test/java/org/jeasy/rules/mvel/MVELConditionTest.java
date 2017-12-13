package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELConditionTest {

    @Test
    public void testMVELExpressionEvaluation() throws Exception {
        // given
        Condition isAdult = new MVELCondition("person.age > 18");
        Facts facts = new Facts();
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = isAdult.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }

    @Test
    public void whenDeclaredFactIsNotPresent_thenShouldReturnFalse() throws Exception {
        // given
        Condition isHot = new MVELCondition("temperature > 30");
        Facts facts = new Facts();

        // when
        boolean evaluationResult = isHot.evaluate(facts);

        // then
        assertThat(evaluationResult).isFalse();
    }
}
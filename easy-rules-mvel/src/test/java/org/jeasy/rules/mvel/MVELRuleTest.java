package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Facts;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELRuleTest {

    private Facts facts = new Facts();
    private MVELRule mvelRule = new MVELRule("rn", "rd", 1);

    @Before
    public void setUp() throws Exception {
        mvelRule.when("person.age > 18");
        mvelRule.then("person.setAdult(true);");
    }

    @Test
    public void whenTheRuleIsTriggered_thenConditionShouldBeEvaluated() throws Exception {
        // given
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = mvelRule.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }

    @Test
    public void whenTheConditionIsTrue_thenActionsShouldBeExecuted() throws Exception {
        // given
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        mvelRule.execute(facts);

        // then
        assertThat(foo.isAdult()).isTrue();
    }
}
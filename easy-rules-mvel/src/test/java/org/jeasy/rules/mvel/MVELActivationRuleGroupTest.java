package org.jeasy.rules.mvel;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELActivationRuleGroupTest {
    private Facts facts = new Facts();
    private MVELActivationRuleGroup activationRuleGroup;
    private MVELRule subrule1 = new AnnotatedMVELRule()
            .name("subrule1")
            .priority(1)
            .when("person.age > 18")
            .then("person.setAdult(true);");
    private MVELRule subrule2 = new AnnotatedMVELRule()
            .name("subrule2")
            .priority(2)
            .when("person.age > 13")
            .then("person.setTeenager(true);");

    @Before
    public void setUp() throws Exception {
        activationRuleGroup = new MVELActivationRuleGroup()
                .name("activation rule group")
                .description("description")
                .priority(1);
    }

    @Test
    public void whenTheRuleIsTriggered_conditionEvaluatesTrue() throws Exception {
        activationRuleGroup.addRule(subrule1);
        activationRuleGroup.addRule(subrule2);

        // given
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = activationRuleGroup.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
        assertThat(((AnnotatedMVELRule) subrule1).isEvaluated()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule2).isEvaluated()).isFalse();
    }

    @Test
    public void whenTheRuleIsTriggered_conditionEvaluatesFalse() throws Exception {
        activationRuleGroup.addRule(subrule1);
        activationRuleGroup.addRule(subrule2);

        // given
        facts.put("person", new Person("foo", 12));

        // when
        boolean evaluationResult = activationRuleGroup.evaluate(facts);

        // then
        assertThat(evaluationResult).isFalse();
        assertThat(((AnnotatedMVELRule) subrule1).isEvaluated()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule2).isEvaluated()).isTrue();
    }

    @Test
    public void whenTheConditionIsTrue_thenActionsShouldBeExecuted() throws Exception {
        activationRuleGroup.addRule(subrule1);
        activationRuleGroup.addRule(subrule2);

        // given
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        activationRuleGroup.evaluate(facts);
        activationRuleGroup.execute(facts);

        // then
        assertThat(foo.isAdult()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule1).isExecuted()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule2).isExecuted()).isFalse();
    }
}

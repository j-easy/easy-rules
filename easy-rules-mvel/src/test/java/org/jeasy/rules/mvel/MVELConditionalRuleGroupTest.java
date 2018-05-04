package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Facts;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELConditionalRuleGroupTest {
    private Facts facts = new Facts();
    private MVELConditionalRuleGroup conditionalRuleGroup;
    private MVELRule subrule1 = new AnnotatedMVELRule()
            .name("subrule1")
            .priority(1)
            .when("person.age > 18")
            .then("person.orderDrink();");
    private MVELRule subrule2 = new AnnotatedMVELRule()
            .name("subrule2")
            .priority(2)
            .when("person.age > 18")
            .then("person.setAdult(true);");

    @Before
    public void setUp() throws Exception {
        conditionalRuleGroup = new MVELConditionalRuleGroup()
                .name("conditional rule group")
                .description("description")
                .priority(1);
    }

    @Test
    public void whenTheRuleIsTriggered_conditionEvaluatesTrue() throws Exception {
        conditionalRuleGroup.addRule(subrule1);
        conditionalRuleGroup.addRule(subrule2);

        // given
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = conditionalRuleGroup.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
        assertThat(((AnnotatedMVELRule) subrule1).isEvaluated()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule2).isEvaluated()).isTrue();
    }

    @Test
    public void whenTheRuleIsTriggered_conditionEvaluatesFalse() throws Exception {
        conditionalRuleGroup.addRule(subrule1);
        conditionalRuleGroup.addRule(subrule2);

        // given
        facts.put("person", new Person("foo", 16));

        // when
        boolean evaluationResult = conditionalRuleGroup.evaluate(facts);

        // then
        assertThat(evaluationResult).isFalse();
        assertThat(((AnnotatedMVELRule) subrule1).isEvaluated()).isFalse();
        assertThat(((AnnotatedMVELRule) subrule2).isEvaluated()).isTrue();
    }

    @Test
    public void whenTheConditionIsTrue_thenActionsShouldBeExecuted() throws Exception {
        conditionalRuleGroup.addRule(subrule1);
        conditionalRuleGroup.addRule(subrule2);

        // given
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        conditionalRuleGroup.evaluate(facts);
        conditionalRuleGroup.execute(facts);

        // then
        assertThat(foo.isAdult()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule1).isExecuted()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule2).isExecuted()).isTrue();
    }
}

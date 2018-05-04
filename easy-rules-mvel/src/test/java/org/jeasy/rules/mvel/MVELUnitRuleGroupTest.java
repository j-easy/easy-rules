package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Facts;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELUnitRuleGroupTest {
    private Facts facts = new Facts();
    private MVELUnitRuleGroup unitRuleGroup;
    private MVELRule subrule1 = new AnnotatedMVELRule()
            .name("subrule1")
            .priority(1)
            .when("person.age > 18")
            .then("person.setAdult(true);");
    private MVELRule subrule2 = new AnnotatedMVELRule()
            .name("subrule2")
            .priority(1)
            .when("person.age >= 21")
            .then("person.setCanDrinkInUS(true);");

    @Before
    public void setUp() throws Exception {
        unitRuleGroup = new MVELUnitRuleGroup()
                .name("unit rule group")
                .description("description")
                .priority(1);
    }

    @Test
    public void whenTheRuleIsTriggered_conditionEvaluatesTrue() throws Exception {
        unitRuleGroup.addRule(subrule1);
        unitRuleGroup.addRule(subrule2);

        // given
        facts.put("person", new Person("foo", 21));

        // when
        boolean evaluationResult = unitRuleGroup.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
        assertThat(((AnnotatedMVELRule) subrule1).isEvaluated()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule2).isEvaluated()).isTrue();
    }

    @Test
    public void whenTheRuleIsTriggered_conditionEvaluatesFalse() throws Exception {
        MVELRule subrule3 = new AnnotatedMVELRule()
                .name("subrule3")
                .priority(1)
                .when("person.age < 18")
                .then("System.out.println(\"Not an adult\");");
        unitRuleGroup.addRule(subrule1);
        unitRuleGroup.addRule(subrule3);

        // given
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = unitRuleGroup.evaluate(facts);

        // then
        assertThat(evaluationResult).isFalse();
        assertThat(((AnnotatedMVELRule) subrule1).isEvaluated()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule3).isEvaluated()).isTrue();
    }

    @Test
    public void whenTheConditionIsTrue_thenActionsShouldBeExecuted() throws Exception {
        unitRuleGroup.addRule(subrule1);
        unitRuleGroup.addRule(subrule2);

        // given
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        unitRuleGroup.execute(facts);

        // then
        assertThat(foo.isAdult()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule1).isExecuted()).isTrue();
        assertThat(((AnnotatedMVELRule) subrule2).isExecuted()).isTrue();
    }
}

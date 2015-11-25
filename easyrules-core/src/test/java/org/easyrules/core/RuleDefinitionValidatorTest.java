package org.easyrules.core;


import org.easyrules.annotation.*;
import org.junit.Before;
import org.junit.Test;

public class RuleDefinitionValidatorTest {

    private RuleDefinitionValidator ruleDefinitionValidator;

    @Before
    public void setup(){
        ruleDefinitionValidator = new RuleDefinitionValidator();
    }

    /*
     * Rule annotation test
     */
    @Test(expected = IllegalArgumentException.class)
    public void notAnnotatedRuleMustNotBeAccepted() {
        ruleDefinitionValidator.validateRuleDefinition(new Object());
    }

    @Test
    public void withCustomAnnotationThatIsItselfAnnotatedWithTheRuleAnnotation() throws Throwable {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithMetaRuleAnnotation());
    }

    /*
     * Conditions methods tests
     */
    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustBeDefined() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithoutCondition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustBePublic() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithNotPublicConditionMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustHaveNoArguments() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithConditionMethodHavingArguments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustReturnBooleanType() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithConditionMethodHavingNonBooleanReturnType());
    }

    /*
     * Action method tests
     */
    @Test(expected = IllegalArgumentException.class)
    public void actionMethodMustBeDefined() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithoutActionMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void actionMethodMustBePublic() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithNotPublicActionMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void actionMethodMustHaveNoArguments() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithActionMethodHavingArguments());
    }

    /*
     * Priority method tests
     */

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodMustBeUnique() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithMoreThanOnePriorityMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodMustBePublic() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithNotPublicPriorityMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodMustHaveNoArguments() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithPriorityMethodHavingArguments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodReturnTypeMustBeInteger() {
        ruleDefinitionValidator.validateRuleDefinition(new AnnotatedRuleWithPriorityMethodHavingNonIntegerReturnType());
    }
}

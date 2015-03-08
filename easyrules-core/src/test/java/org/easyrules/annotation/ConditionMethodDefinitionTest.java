package org.easyrules.annotation;

import org.easyrules.core.AnnotatedRulesEngine;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for condition method definition.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class ConditionMethodDefinitionTest {

    private AnnotatedRulesEngine rulesEngine;

    @Before
    public void setup(){
        rulesEngine = new AnnotatedRulesEngine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustBeDefined() {
        rulesEngine.registerRule(new AnnotatedRuleWithoutCondition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustBePublic() {
        rulesEngine.registerRule(new AnnotatedRuleWithNotPublicConditionMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustHaveNoArguments() {
        rulesEngine.registerRule(new AnnotatedRuleWithConditionMethodHavingArguments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustReturnBooleanType() {
        rulesEngine.registerRule(new AnnotatedRuleWithConditionMethodHavingNonBooleanReturnType());
    }



}

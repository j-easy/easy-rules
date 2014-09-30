package io.github.benas.easyrules.core.test.annotation.condition;

import io.github.benas.easyrules.core.AnnotatedRulesEngine;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for condition method definition.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class ConditionMethodDefinitionTest {

    private AnnotatedRulesEngine rulesEngine;

    @Before
    public void setup(){
        rulesEngine = new AnnotatedRulesEngine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustBeDefined() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithoutCondition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustBePublic() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithNotPublicConditionMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustHaveNoArguments() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithConditionMethodHavingArguments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conditionMethodMustReturnBooleanType() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithConditionMethodHavingNonBooleanReturnType());
    }



}

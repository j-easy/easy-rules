package org.easyrules.annotation;

import org.easyrules.core.AnnotatedRulesEngine;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for action method definition.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class PriorityMethodDefinitionTest {

    private AnnotatedRulesEngine rulesEngine;

    @Before
    public void setup(){
        rulesEngine = new AnnotatedRulesEngine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodMustBeUnique() {
        rulesEngine.registerRule(new AnnotatedRuleWithMoreThanOnePriorityMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodMustBePublic() {
        rulesEngine.registerRule(new AnnotatedRuleWithNotPublicPriorityMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodMustHaveNoArguments() {
        rulesEngine.registerRule(new AnnotatedRuleWithPriorityMethodHavingArguments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodReturnTypeMustBeInteger() {
        rulesEngine.registerRule(new AnnotatedRuleWithPriorityMethodHavingNonIntegerReturnType());
    }

}

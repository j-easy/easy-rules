package org.easyrules.core.test.annotation.priority;

import org.easyrules.core.AnnotatedRulesEngine;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for action method definition.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class PriorityMethodDefinitionTest {

    private AnnotatedRulesEngine rulesEngine;

    @Before
    public void setup(){
        rulesEngine = new AnnotatedRulesEngine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodMustBeUnique() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithMoreThanOnePriorityMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodMustBePublic() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithNotPublicPriorityMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodMustHaveNoArguments() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithPriorityMethodHavingArguments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void priorityMethodReturnTypeMustBeInteger() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithPriorityMethodHavingNonIntegerReturnType());
    }

}

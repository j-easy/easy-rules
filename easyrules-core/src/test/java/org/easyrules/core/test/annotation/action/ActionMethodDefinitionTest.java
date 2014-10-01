package org.easyrules.core.test.annotation.action;

import org.easyrules.core.AnnotatedRulesEngine;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for action method definition.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class ActionMethodDefinitionTest {

    private AnnotatedRulesEngine rulesEngine;

    @Before
    public void setup(){
        rulesEngine = new AnnotatedRulesEngine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void actionMethodMustBeDefined() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithoutActionMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void actionMethodMustBePublic() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithNotPublicActionMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void actionMethodMustHaveNoArguments() {
        //an exception should be throw at rule registration time
        rulesEngine.registerRule(new AnnotatedRuleWithActionMethodHavingArguments());
    }

}

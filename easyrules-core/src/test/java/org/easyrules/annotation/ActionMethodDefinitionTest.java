package org.easyrules.annotation;

import org.easyrules.core.AnnotatedRulesEngine;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for action method definition.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class ActionMethodDefinitionTest {

    private AnnotatedRulesEngine rulesEngine;

    @Before
    public void setup(){
        rulesEngine = new AnnotatedRulesEngine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void actionMethodMustBeDefined() {
        rulesEngine.registerRule(new AnnotatedRuleWithoutActionMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void actionMethodMustBePublic() {
        rulesEngine.registerRule(new AnnotatedRuleWithNotPublicActionMethod());
    }

    @Test(expected = IllegalArgumentException.class)
    public void actionMethodMustHaveNoArguments() {
        rulesEngine.registerRule(new AnnotatedRuleWithActionMethodHavingArguments());
    }

}

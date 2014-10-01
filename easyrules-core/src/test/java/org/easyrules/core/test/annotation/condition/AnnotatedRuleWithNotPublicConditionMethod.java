package org.easyrules.core.test.annotation.condition;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class AnnotatedRuleWithNotPublicConditionMethod {

    private boolean executed;

    @Condition
    private boolean when() {
        return true;
    }

    @Action
    public void then() throws Exception {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}

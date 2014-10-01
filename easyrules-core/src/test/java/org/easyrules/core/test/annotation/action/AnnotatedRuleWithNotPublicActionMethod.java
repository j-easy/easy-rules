package org.easyrules.core.test.annotation.action;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class AnnotatedRuleWithNotPublicActionMethod {

    private boolean executed;

    @Condition
    private boolean when() {
        return true;
    }

    @Action
    private void then() throws Exception {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}

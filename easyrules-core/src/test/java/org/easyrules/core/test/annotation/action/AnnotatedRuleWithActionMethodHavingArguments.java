package org.easyrules.core.test.annotation.action;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class AnnotatedRuleWithActionMethodHavingArguments {

    private boolean executed;

    @Condition
    public boolean when() {
        return true;
    }

    @Action
    public void then(int i) throws Exception {
        if (i == 1) {
            executed = true;
        }
    }

    public boolean isExecuted() {
        return executed;
    }

}

package io.github.benas.easyrules.core.test.annotation.action;

import io.github.benas.easyrules.annotation.Action;
import io.github.benas.easyrules.annotation.Condition;
import io.github.benas.easyrules.annotation.Rule;

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

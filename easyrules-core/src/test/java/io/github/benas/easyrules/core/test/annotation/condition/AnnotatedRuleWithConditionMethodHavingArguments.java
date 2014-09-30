package io.github.benas.easyrules.core.test.annotation.condition;

import io.github.benas.easyrules.annotation.Action;
import io.github.benas.easyrules.annotation.Condition;
import io.github.benas.easyrules.annotation.Rule;

@Rule
public class AnnotatedRuleWithConditionMethodHavingArguments {

    private boolean executed;

    @Condition
    public boolean when(int i) {
        return i == 0;
    }

    @Action
    public void then() throws Exception {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}

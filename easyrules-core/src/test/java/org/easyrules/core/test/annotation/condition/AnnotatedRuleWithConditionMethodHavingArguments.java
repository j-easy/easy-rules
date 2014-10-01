package org.easyrules.core.test.annotation.condition;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

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

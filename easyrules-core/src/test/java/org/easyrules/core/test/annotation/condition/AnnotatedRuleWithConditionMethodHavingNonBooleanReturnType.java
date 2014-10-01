package org.easyrules.core.test.annotation.condition;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class AnnotatedRuleWithConditionMethodHavingNonBooleanReturnType {

    private boolean executed;

    @Condition
    public int when() {
        return 0;
    }

    @Action
    public void then() throws Exception {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}

package io.github.benas.easyrules.core.test.annotation.priority;

import io.github.benas.easyrules.annotation.Action;
import io.github.benas.easyrules.annotation.Condition;
import io.github.benas.easyrules.annotation.Priority;
import io.github.benas.easyrules.annotation.Rule;

@Rule
public class AnnotatedRuleWithPriorityMethodHavingNonIntegerReturnType {

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

    @Priority
    public String getPriority() {
        return "1";
    }

}

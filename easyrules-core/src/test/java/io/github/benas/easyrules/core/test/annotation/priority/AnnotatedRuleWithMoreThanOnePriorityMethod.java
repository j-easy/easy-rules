package io.github.benas.easyrules.core.test.annotation.priority;

import io.github.benas.easyrules.annotation.Action;
import io.github.benas.easyrules.annotation.Condition;
import io.github.benas.easyrules.annotation.Priority;
import io.github.benas.easyrules.annotation.Rule;

@Rule
public class AnnotatedRuleWithMoreThanOnePriorityMethod {

    private boolean executed;

    @Condition
    public boolean when() {
        return true;
    }

    @Action
    public void then() throws Exception {
        executed = true;
    }

    @Priority
    public int getPriority() {
        return 0;
    }

    @Priority
    public int getRulePriority() {
        return 1;
    }

    public boolean isExecuted() {
        return executed;
    }

}

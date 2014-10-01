package org.easyrules.core.test.annotation.priority;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

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

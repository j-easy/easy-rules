package io.github.benas.easyrules.core.test.annotation.condition;

import io.github.benas.easyrules.annotation.Action;
import io.github.benas.easyrules.annotation.Rule;

@Rule
public class AnnotatedRuleWithoutCondition {

    private boolean executed;

    public boolean when() {
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

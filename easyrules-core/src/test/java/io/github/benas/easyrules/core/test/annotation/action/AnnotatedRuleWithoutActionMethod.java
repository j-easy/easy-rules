package io.github.benas.easyrules.core.test.annotation.action;

import io.github.benas.easyrules.annotation.Condition;
import io.github.benas.easyrules.annotation.Rule;

@Rule
public class AnnotatedRuleWithoutActionMethod {

    private boolean executed;

    @Condition
    public boolean when() {
        return true;
    }

    public void then() throws Exception {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}

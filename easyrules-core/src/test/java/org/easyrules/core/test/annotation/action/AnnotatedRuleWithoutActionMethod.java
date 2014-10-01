package org.easyrules.core.test.annotation.action;

import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

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

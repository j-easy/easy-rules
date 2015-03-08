package org.easyrules.annotation;

@Rule
public class AnnotatedRuleWithNotPublicConditionMethod {

    private boolean executed;

    @Condition
    private boolean when() {
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

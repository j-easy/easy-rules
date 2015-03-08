package org.easyrules.annotation;

@Rule
public class AnnotatedRuleWithNotPublicPriorityMethod {

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
    private int getPriority() {
        return 1;
    }

}

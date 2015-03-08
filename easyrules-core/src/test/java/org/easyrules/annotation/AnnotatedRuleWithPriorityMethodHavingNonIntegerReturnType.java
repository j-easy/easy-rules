package org.easyrules.annotation;

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

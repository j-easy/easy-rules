package org.easyrules.annotation;

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

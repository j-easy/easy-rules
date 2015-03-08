package org.easyrules.annotation;

@Rule
public class AnnotatedRuleWithNotPublicActionMethod {

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

}

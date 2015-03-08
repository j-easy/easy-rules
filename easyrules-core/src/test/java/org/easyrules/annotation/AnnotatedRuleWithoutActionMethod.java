package org.easyrules.annotation;

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

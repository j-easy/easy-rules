package org.easyrules.annotation;

@Rule
public class AnnotatedRuleWithActionMethodHavingArguments {

    private boolean executed;

    @Condition
    public boolean when() {
        return true;
    }

    @Action
    public void then(int i) throws Exception {
        if (i == 1) {
            executed = true;
        }
    }

    public boolean isExecuted() {
        return executed;
    }

}

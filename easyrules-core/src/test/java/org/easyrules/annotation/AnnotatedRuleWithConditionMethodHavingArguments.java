package org.easyrules.annotation;

@Rule
public class AnnotatedRuleWithConditionMethodHavingArguments {

    private boolean executed;

    @Condition
    public boolean when(int i) {
        return i == 0;
    }

    @Action
    public void then() throws Exception {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}

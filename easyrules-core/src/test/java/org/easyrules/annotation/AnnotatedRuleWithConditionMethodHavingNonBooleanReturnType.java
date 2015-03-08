package org.easyrules.annotation;

@Rule
public class AnnotatedRuleWithConditionMethodHavingNonBooleanReturnType {

    private boolean executed;

    @Condition
    public int when() {
        return 0;
    }

    @Action
    public void then() throws Exception {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}

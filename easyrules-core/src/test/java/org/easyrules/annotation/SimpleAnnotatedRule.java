package org.easyrules.annotation;

@Rule(name = "myRule", description = "my rule description")
public class SimpleAnnotatedRule {

    private boolean executed;

    private String actionSequence = "";

    @Condition
    public boolean when() {
        return condition1() && condition2() || condition3();
    }

    private boolean condition1() {
        return true;
    }

    private boolean condition2() {
        return false;
    }

    private boolean condition3() {
        return true;
    }

    @Action
    public void then0() throws Exception {
        actionSequence += "0";
    }

    @Action(order = 1)
    public void then1() throws Exception {
        actionSequence += "1";
    }

    @Action(order = 2)
    public void then2() throws Exception {
        actionSequence += "2";
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

    public String getActionSequence() {
        return actionSequence;
    }

}

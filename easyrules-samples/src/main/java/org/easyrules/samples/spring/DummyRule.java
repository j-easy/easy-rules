package org.easyrules.samples.spring;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "dummy rule")
public class DummyRule {

    @Condition
    public boolean when() {
        return true;
    }

    @Action
    public void then(){
        System.out.println("Hey, I'm managed by Spring");
    }
}

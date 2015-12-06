package org.easyrules.samples.spring;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.spring.SpringRule;

@SpringRule
public class AnotherDummyRule {

    @Condition
    public boolean when() {
        return true;
    }

    @Action
    public void then(){
        System.out.println("Hey, I'm annotated with @SpringRule");
    }
}

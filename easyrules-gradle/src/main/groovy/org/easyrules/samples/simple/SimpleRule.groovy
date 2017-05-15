package org.easyrules.samples.simple

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Priority
import org.easyrules.annotation.Rule

@Rule
public class SimpleRule  {

    @Condition
    public boolean when() { true }

    @Action
    public void then() { println "Easy Rules rocks!" }
    
}

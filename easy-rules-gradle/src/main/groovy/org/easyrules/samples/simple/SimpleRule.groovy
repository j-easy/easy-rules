package org.easyrules.samples.simple

import Action
import Condition
import Priority
import Rule

@Rule
public class SimpleRule  {

    @Condition
    public boolean when() { true }

    @Action
    public void then() { println "Easy Rules rocks!" }
    
}

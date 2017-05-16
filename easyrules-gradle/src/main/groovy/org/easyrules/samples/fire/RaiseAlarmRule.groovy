package org.easyrules.samples.fire

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule
class RaiseAlarmRule {

    def theWorld

    @Condition
    boolean when() {
    	theWorld.fires.size() > 0
    }

    @Action
    def then() { 
    	theWorld.alarm = new Alarm()
        println( "Raise the Alarm");
    }

    @Priority
    int getPriority() { 0 }

}

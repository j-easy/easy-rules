package org.easyrules.samples.fire

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule
class CancelAlarmRule {

    def theWorld

    @Condition
    boolean when() {
    	theWorld.alarm != null && theWorld.fires.size() == 0
    }

    @Action
    def then() { 
        theWorld.alarm = null
        println( "Cancel the Alarm");
    }

    @Priority
    int getPriority() { 0 }

}

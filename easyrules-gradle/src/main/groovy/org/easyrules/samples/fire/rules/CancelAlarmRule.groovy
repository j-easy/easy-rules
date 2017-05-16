package org.easyrules.samples.fire.rules

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule(description='All the fires are out, cancel the alarm')
class CancelAlarmRule {

    def theWorld

    @Condition
    boolean when() {
    	theWorld.alarm  && theWorld.fires.size() == 0
    }

    @Action
    def then() { 
        println( "Cancel the Alarm");
        theWorld.alarm = null
    }

    @Priority
    int getPriority() { 0 }

}

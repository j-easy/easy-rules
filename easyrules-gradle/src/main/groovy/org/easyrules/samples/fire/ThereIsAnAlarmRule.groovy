package org.easyrules.samples.fire

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule
class ThereIsAnAlarmRule {

    def theWorld

    @Condition
    boolean when() {
    	theWorld.alarm != null
    }

    @Action
    def then() { 
        println 'To Fire Station: There is an Alarm'    
    }

    @Priority
    int getPriority() { 0 }

}

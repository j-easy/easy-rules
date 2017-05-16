package org.easyrules.samples.fire.rules

import org.easyrules.samples.fire.beans.Alarm

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule(description='A fire will raise an alarm')
class RaiseAlarmRule {

    def theWorld

    @Condition
    boolean when() {
    	theWorld.fires.size() > 0
    }

    @Action
    def then() { 
    	theWorld.alarm = new Alarm('123 Main Street')
        println( "Raise the Alarm");
    }

    @Priority
    int getPriority() { 0 }

}

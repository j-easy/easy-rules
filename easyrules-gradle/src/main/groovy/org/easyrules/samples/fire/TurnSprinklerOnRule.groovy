package org.easyrules.samples.fire

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule
class TurnSprinklerOnRule {

    def theWorld

    @Condition
    boolean when() {
        theWorld.fires.size() > 0
    }

    @Action
    def then() { 
        theWorld.fires.each{ fire ->
    	   println "Turn sprinkler on in room: ${fire.room.name}"
        }
    }

    @Priority
    int getPriority() { 0 }

}

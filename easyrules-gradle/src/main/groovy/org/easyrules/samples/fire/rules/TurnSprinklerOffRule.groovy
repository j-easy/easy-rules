package org.easyrules.samples.fire.rules

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule(description='The alarm has been cancelled, turn off all of the sprinklers')
class TurnSprinklerOffRule {

    def theWorld

    @Condition
    boolean when() {
        theWorld.alarm == null
    }

    @Action
    def then() { 
        theWorld.sprinklers.each { sprinkler ->
            if(sprinkler.on) {
               sprinkler.on = false
    	       println "Turn sprinkler off in room: ${sprinkler.room.name}"
            }
        }
    }

    @Priority
    int getPriority() { 5 }

}

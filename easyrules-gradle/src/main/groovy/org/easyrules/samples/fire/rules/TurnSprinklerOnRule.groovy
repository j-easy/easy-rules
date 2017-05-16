package org.easyrules.samples.fire.rules

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule(description='A fire has been detected in a room, turn on the sprinkler in the room; Highest priority rule')
class TurnSprinklerOnRule {

    def theWorld

    @Condition
    boolean when() {
        theWorld.fires.size() > 0
    }

    @Action
    def then() { 
        theWorld.fires.each { fire ->

            def sprinkler = theWorld.sprinklers.find { sprinkler ->
                sprinkler.room.name == fire.room.name

            }

            if(sprinkler) {
               sprinkler.on = true
    	       println "Turn sprinkler on in room: ${sprinkler.room.name}"
            }
        }
    }

    @Priority
    int getPriority() { 0 }

}

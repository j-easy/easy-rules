package org.easyrules.samples.fire.rules

import Action
import Condition
import Rule
import Priority

@Rule(description='No alarm, nothing to see here; This need to be last rule considered')
class EverythingOKRule {

    def theWorld

    @Condition
    boolean when() {
        theWorld.alarm == null
    }

    @Action
    def then() { 
    	println 'At the Fire Station: Everything is OK' 
    }

    @Priority
    int getPriority() { 15 }

}

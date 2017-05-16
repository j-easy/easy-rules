package org.easyrules.samples.fire.rules

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule(description='No alarm, nothing to see here; This need to be last rule considered')
class EverythingOKRule {

    def theWorld

    @Condition
    boolean when() {
        theWorld.alarm == null
    }

    @Action
    def then() { 
    	println 'To Fire Station: Everything is OK' 
    }

    @Priority
    int getPriority() { 15 }

}

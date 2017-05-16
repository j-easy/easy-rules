package org.easyrules.samples.fire

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule
import org.easyrules.annotation.Priority

@Rule
class EverythingOKRule {

    def theWorld

    @Condition
    boolean when() {
    	def anyOn = theWorld.sprinklers.any{it.isOn()}
    	(anyOn == false) && (theWorld.alarm == null)
    }

    @Action
    def then() { 
    	println 'To Fire Station: Everything is OK' 
    }

    @Priority
    int getPriority() { 10 }

}

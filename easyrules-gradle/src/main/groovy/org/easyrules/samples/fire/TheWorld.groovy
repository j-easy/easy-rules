package org.easyrules.samples.fire

@groovy.transform.TupleConstructor
@groovy.transform.EqualsAndHashCode
@groovy.transform.ToString
class TheWorld {

    Sprinkler[] sprinklers
    Alarm alarm = null
    def fires = []

    TheWorld(sprinklers) {
    	this.sprinklers = sprinklers
    }
}

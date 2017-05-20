package org.easyrules.samples.fire

@groovy.transform.TupleConstructor
@groovy.transform.EqualsAndHashCode
@groovy.transform.ToString(includePackage = false, includeNames = true)
class TheWorld {

    def sprinklers = []
    def alarm = null
    def fires = []

}

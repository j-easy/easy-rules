package org.easyrules.samples.fire.beans

@groovy.transform.TupleConstructor
@groovy.transform.EqualsAndHashCode
@groovy.transform.ToString
class Sprinkler {
    def room;
    def on = false;
}

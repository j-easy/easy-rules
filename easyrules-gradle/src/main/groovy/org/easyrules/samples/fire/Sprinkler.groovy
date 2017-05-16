package org.easyrules.samples.fire

@groovy.transform.TupleConstructor
@groovy.transform.EqualsAndHashCode
@groovy.transform.ToString
class Sprinkler {
    Room room;
    boolean on;

    boolean isOn() {on}
}

package org.easyrules.samples.shop

import groovy.transform.TupleConstructor
import groovy.transform.EqualsAndHashCode

@TupleConstructor
@EqualsAndHashCode(includes='name,age')
class Person {

    def name
    def age
    boolean adult
}

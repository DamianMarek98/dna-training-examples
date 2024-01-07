package dna.example.demo.construction.elements.examples

import spock.lang.Specification

class ValueObjectTest extends Specification {
    def "negative capacity should not be accepted"() {
        when:
        ValueObject.of(-10)
        then:
        thrown(CapacityCannotBeNegativeException)
    }

    //etc for every method, factory methods, possibly mutation tests
}

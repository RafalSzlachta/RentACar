package pl.sda.rentacar.develop

import spock.lang.Specification

//TODO remove when tests will be added with notice number RAC-29
class SimpleTestSpec extends Specification{

    def "ShouldReturn3WhenGiven1And2"() {
        given:
            int firstNumber = 1
            int secondNumber = 2

        when:
            int result = SimpleTest.add(1, 2)

        then:
            result == 3
    }
}

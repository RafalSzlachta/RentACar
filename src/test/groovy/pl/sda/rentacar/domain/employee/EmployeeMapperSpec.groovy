package pl.sda.rentacar.domain.employee

import pl.sda.rentacar.domain.department.Department
import spock.lang.Specification

class EmployeeMapperSpec extends Specification {

    def department = new Department(1L, "Opole", [], [])

    def 'should map EmployeeCreateRequest to Employee'() {
        given:
        def request = new EmployeeCreateRequest("Robert", "Kubica", department)

        when:
        def result = EmployeeMapper.MAPPER.mapToEmployee(request)

        then:
        result.id == null
        result.firstName == request.firstName
        result.lastName == request.lastName
        result.department == request.department
    }

    def "should map Employee to EmployeeView"() {
        given:
        def request = new Employee(1L, "Jerzy", "Gekon", department)

        when:
        def result = EmployeeMapper.MAPPER.mapToEmployeeView(request)

        then:
        result.firstName == request.firstName
        result.lastName == request.lastName
        result.department == request.department
    }
}

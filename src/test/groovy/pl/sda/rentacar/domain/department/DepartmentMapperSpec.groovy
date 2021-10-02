package pl.sda.rentacar.domain.department

import spock.lang.Shared
import spock.lang.Specification

class DepartmentMapperSpec extends Specification {

    @Shared
    def department = new Department(1L, "Opole", [] as Set, [] as Set)


    def 'should map Department to DepartmentSingleView'() {
        when:
        def result = DepartmentMapper.MAPPER.toDepartmentSingleView(department)

        then:
        result.address == department.address
        result.employees == department.employees
        result.cars == department.cars
    }

    def 'should map Department to DepartmentListView'() {
        when:
        def result = DepartmentMapper.MAPPER.toDepartmentListView(department)

        then:
        result.id == department.id
        result.address == department.address

    }

    def 'should map DepartmentCreateRequest to Department'() {
        given:
        def request = new DepartmentCreateRequest("Opole", [] as Set, [] as Set)

        when:
        def result = DepartmentMapper.MAPPER.toDepartment(request)

        then:
        result.address == request.address
        result.employees == request.employees
        result.cars == request.cars

    }
}
package pl.sda.rentacar.domain.department

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class DepartmentServiceIntegrationSpec extends Specification {

    @Autowired
    private DepartmentRepository repository

    @Autowired
    private DepartmentService service

    def cleanup() {
        repository.deleteAll()
    }

    @Shared
    def department = new DepartmentCreateRequest("Sosnowiec", [] as Set, [] as Set)

    def 'should add department'() {
        given:
        service.addDepartment(department)

        when:
        def result = repository.findAll()

        then:
        with(result.first()) {
            id != null
            address == department.address
            employees == department.employees
            cars == department.cars
        }
    }

    def 'should return department by given id if exist'() {
        given:
        service.addDepartment(department)
        def all = repository.findAll()
        def id = all.first().id

        when:
        def result = service.getDepartmentById(id)

        then:
        with(result) {
            address == department.address
            employees == department.employees
            cars == department.cars
        }
    }

    def 'should throw DepartmentNotFoundException'() {
        when:
        service.getDepartmentById(10L)

        then:
        def e = thrown(DepartmentNotFoundException)
        e.message == "department with id: 10 doesn't exists"
    }

    def 'should add two departments and return two departments'() {
        given:
        def otherDepartment = new DepartmentCreateRequest("Radom", [] as Set, [] as Set)

        when:
        service.addDepartment(department)
        service.addDepartment(otherDepartment)

        and:
        def departments = service.getAllDepartments()

        then:
        departments != null
        departments.size() == 2
        departments.last().address == otherDepartment.address
    }

    def 'should update department with given id'() {
        given:
        def departmentUpdateRequest = new DepartmentCreateRequest("Mielno", [] as Set, [] as Set)

        and:
        service.addDepartment(department)
        def id = repository.findAll().first().getId()

        when:
        service.updateDepartment(id, departmentUpdateRequest)
        def updatedDepartment = service.getDepartmentById(id)

        then:
        updatedDepartment.address == departmentUpdateRequest.address
    }

    def 'should remove department with given id'() {
        given:
        service.addDepartment(department)
        def id = repository.findAll().first().id

        when:
        service.removeDepartment(id)

        then:
        repository.findAll().isEmpty()
    }

}

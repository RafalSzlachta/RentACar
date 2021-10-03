package pl.sda.rentacar.domain.employee

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.sda.rentacar.domain.department.DepartmentCreateRequest
import pl.sda.rentacar.domain.department.DepartmentService
import spock.lang.Shared
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class EmployeeServiceIntegrationSpec extends Specification {

    @Autowired
    private EmployeeService service

    @Autowired
    private DepartmentService departmentService

    @Autowired
    private EmployeeRepository repository

    @Shared
    def department = new DepartmentCreateRequest("Opole", [] as Set, [] as Set)

    @Shared
    def department2 = new DepartmentCreateRequest("Warsaw", [] as Set, [] as Set)

    @Shared
    def department3 = new DepartmentCreateRequest("Lodz",[] as Set,[] as Set)

    def 'should add and find employees'() {
        given:
        def departmentId = givenDepartmentExists(department)
        def department2Id = givenDepartmentExists(department2)
        def employee = new EmployeeCreateRequest("Lando", "Morris", departmentId)
        def employee2 = new EmployeeCreateRequest("Carlos","Szajs", department2Id)
        service.addEmployee(employee)
        service.addEmployee(employee2)

        when:

        def result = service.findAllEmployees()

        then:
        result != null
        result.size() == 2
        with(result.first()) {
            firstName == employee.firstName
            lastName == employee.lastName
            it.department == department
        }
        with(result.last()) {
            firstName == employee2.firstName
            lastName == employee2.lastName
            it.department == department
        }
    }

    def 'should search and destroy employee with given id'() {
        given:
        def department3Id = givenDepartmentExists(department3)
        def employee3 = new EmployeeCreateRequest("Mick", "Szumacher", department3Id)
        service.addEmployee(employee3)
        def id = repository.findAll().first().id

        when:
        service.deleteEmployeeById(id)

        then:
        repository.findById(id).isEmpty()
    }


    private def givenDepartmentExists(DepartmentCreateRequest request) {
       return departmentService.addDepartment(request)
    }
}

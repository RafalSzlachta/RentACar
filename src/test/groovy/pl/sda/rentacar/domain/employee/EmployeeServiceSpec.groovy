package pl.sda.rentacar.domain.employee

import pl.sda.rentacar.domain.car.Car
import pl.sda.rentacar.domain.department.Department
import pl.sda.rentacar.domain.department.DepartmentService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class EmployeeServiceSpec extends Specification {

    def departmentService = Mock(DepartmentService)
    def repository = Mock(EmployeeRepository)

    @Subject
    def service = new EmployeeService(departmentService, repository)

    @Shared
    def department = new Department(1L, "Opole", [] as Set<Employee>, [] as Set<Car>)

    @Shared
    def department2 = new Department(2L, "Warszawa", [] as Set<Employee>, [] as Set<Car>)

    def 'should call repository save on save'() {
        given:
        def employee = new EmployeeCreateRequest("Lewis", "Oponiarz", department.id)
        def expectedEmployeeArgument = new Employee("Lewis", "Oponiarz", department)

        and:
        departmentService.findDepartmentById(_ as Long) >> department

        when:
        service.addEmployee(employee)

        then:
        1 * repository.save(expectedEmployeeArgument)
    }

    def 'should find employee by id'() {
        given:
        def id = 1L
        def employee = new Employee(id, "Fernando", "Bonzo", department)
        repository.findById(id) >> Optional.of(employee)

        when:
        def result = service.getEmployeeById(id)

        then:
        with(result) {
            firstName == employee.firstName
            lastName == employee.lastName
            department == employee.department
        }
    }

    def 'should find all employees'() {
        given:
        def employee = new Employee(1L, "Valtteri", "Doggas", department)
        def employee2 = new Employee(2L, "Nikita", "Mazespin", department2)
        repository.findAll() >> [employee, employee2]

        when:
        def result = service.findAllEmployees()

        then:
        result != null
        result.size() == 2
        with(result.first()) {
            firstName == employee.firstName
            lastName == employee.lastName
            department == employee.department
        }
        with(result.last()) {
            firstName == employee2.firstName
            lastName == employee2.lastName
            department == employee2.department
        }
    }

    def 'should throw EmployeeNotFoundException'() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.findEmployeeById(3L)

        then:
        def e = thrown(EmployeeNotFoundException)
        e.message == "Employee with id 3 does not exist"
    }
}

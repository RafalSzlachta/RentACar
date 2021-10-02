package pl.sda.rentacar.domain.employee

import pl.sda.rentacar.domain.department.Department
import spock.lang.Specification

class EmployeeServiceSpec extends Specification {

    def department = new Department(1L, "Opole", [], [])
    def repository = Mock(EmployeeRepository)
    def service = new EmployeeService(repository)

    def 'should call repository save on save'() {
        given:
        def employee = new EmployeeCreateRequest("Lewis", "Oponiarz", department)
        def expectedEmployeeArgument = new Employee("Lewis", "Oponiarz", department)

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

    def department2 = new Department(1L, "Warszawa", [], [])

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

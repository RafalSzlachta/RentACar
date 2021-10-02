package pl.sda.rentacar.domain.department

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class DepartmentServiceIntegrationSpec extends Specification {

    @Autowired
    private final DepartmentRepository repository

    @Autowired
    private final DepartmentService service

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
        with(result.first()){
            id != null
            address == department.address
            employees == department.employees
            cars == department.cars
        }
    }

}

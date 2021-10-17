package pl.sda.rentacar.domain.car

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.sda.rentacar.domain.department.Department
import pl.sda.rentacar.domain.department.DepartmentCreateRequest
import pl.sda.rentacar.domain.department.DepartmentRepository
import pl.sda.rentacar.domain.department.DepartmentService
import pl.sda.rentacar.domain.employee.Employee
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class CarServiceIntegrationSpec extends Specification {

    @Autowired
    private CarRepository repository

    @Autowired
    private CarService service

    @Autowired
    private DepartmentService departmentService

    def 'should add car'() {
        given:
        def department = new DepartmentCreateRequest("Warszawa", [] as Set<Employee>, [] as Set<Car>)
        def departmentId = departmentService.addDepartment(department)
        def car = carCreateRequestWithDepartmentId(departmentId)

        when:
        service.addCar(car)

        then:
        def result = repository.findAll().first()
        with(result) {
            id != null
            make == car.make
            model == car.model
            productionYear == car.productionYear
            pricePerDay == car.pricePerDay
            bodyType == car.bodyType
            departmentId == car.departmentId
        }
    }

//    def cleanup() {
//        repository.deleteAll()
//    }

    private static CarCreateRequest carCreateRequestWithDepartmentId(Long departmentId) {
        new CarCreateRequest(
                "Ford",
                "Focus",
                1324,
                BigDecimal.valueOf(100L),
                BodyType.PICKUP,
                departmentId
        )
    }
}
package pl.sda.rentacar.domain.car

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.sda.rentacar.domain.department.Department
import pl.sda.rentacar.domain.department.DepartmentRepository
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class CarServiceIntegrationSpec extends Specification{

    @Autowired
    private CarRepository repository

    @Autowired
    private CarService service

    @Autowired
    private DepartmentRepository departmentRepository

    @Shared
    def department = new Department(
            13L,
            "Zimna Wodka",
            [] as Set,
            [] as Set
    )

    @Shared
    def car = new CarCreateRequest(
            "Ford",
            "Focus",
            1324,
            BigDecimal.valueOf(100L),
            BodyType.PICKUP,
            department.getId()
            )

    def 'should add car'(){
        given:
        cleanup()
        departmentRepository.save(department)
        service.addCar(car)

        when:
        def result = repository.findAll().first()

        then:
        with(result){
            id != null
            make == car.make
            model == car.model
            productionYear == car.productionYear
            pricePerDay == car.pricePerDay
            bodyType == car.bodyType
            departmentId == car.departmentId
        }
    }

    def cleanup() {
        repository.deleteAll()
    }
}
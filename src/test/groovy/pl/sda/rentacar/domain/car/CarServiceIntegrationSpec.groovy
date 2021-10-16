package pl.sda.rentacar.domain.car

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class CarServiceIntegrationSpec extends Specification{

    @Autowired
    private CarRepository repository

    @Autowired
    private CarService service

    @Shared
    def car = new CarCreateRequest(
            "Ford",
            "Focus",
            1324,
            BigDecimal.valueOf(100L),
            BodyType.PICKUP
            )
    def 'should add car'(){
        given:
        cleanup()
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
        }
    }

    def cleanup() {
        repository.deleteAll()
    }
}
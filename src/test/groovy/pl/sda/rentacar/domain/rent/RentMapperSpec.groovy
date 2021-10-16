package pl.sda.rentacar.domain.rent

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.sda.rentacar.domain.car.BodyType
import pl.sda.rentacar.domain.car.Car
import pl.sda.rentacar.domain.car.CarStatus
import pl.sda.rentacar.domain.client.Client
import pl.sda.rentacar.domain.department.Department
import pl.sda.rentacar.domain.employee.Employee
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
@ContextConfiguration
class RentMapperSpec extends Specification{

    @Shared
    public def clientDaniel = new Client(
            1L,
            "Daniel",
            "Olbrychski",
            "kochamskutery@szabelka.eu",
            "777888999"
    )


    @Shared
    def departmentZadupie = new Department(
            1L,
            "Zadupie",
            [] as Set,
            [] as Set)

    @Shared
    def employeeMax = new Employee(
            1L,
            "Max",
            "Wersztapen",
            departmentZadupie)

    @Shared
    def carForFamily = new Car(
            1L,
            "Renaul",
            "Espace",
            2007,
            CarStatus.AVAILABLE,
            BigDecimal.valueOf(110),
            BodyType.MINIVAN
    )

    def 'should map Rent to RentView'() {
        given:
        def request = new Rent(
                1L,
                clientDaniel,
                employeeMax,
                carForFamily,
                LocalDate.now().minusDays(1L),
                LocalDate.now(),
                carForFamily.getPricePerDay(),
                "tanio",
                RentStatus.FINISHED)

        when:
        def result = RentMapper.MAPPER.mapToRentView(request)

        then:
        result.client == request.client
        result.employee == request.employee
        result.startDate == request.startDate
        result.returnDate == request.returnDate
        result.charge == request.charge
        result.comment == request.comment
    }
}

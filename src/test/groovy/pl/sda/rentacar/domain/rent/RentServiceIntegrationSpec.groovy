package pl.sda.rentacar.domain.rent

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.sda.rentacar.domain.car.BodyType
import pl.sda.rentacar.domain.car.Car
import pl.sda.rentacar.domain.car.CarStatus
import pl.sda.rentacar.domain.client.Client
import pl.sda.rentacar.domain.client.ClientCreateRequest
import pl.sda.rentacar.domain.client.ClientRepository
import pl.sda.rentacar.domain.client.ClientService
import pl.sda.rentacar.domain.department.Department
import pl.sda.rentacar.domain.employee.Employee
import pl.sda.rentacar.domain.employee.EmployeeService
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
@ContextConfiguration
class RentServiceIntegrationSpec extends Specification {

    @Autowired
    RentService service

    @Autowired
    RentRepository repository

    @Autowired
    ClientService clientService

    @Autowired
    EmployeeService employeeService

    @Shared
    def department = new Department(
            1L,
            "Lodz",
            [] as Set,
            [] as Set
    )

    @Shared
    def clientRequest = new ClientCreateRequest(
            "Otylia",
            "Jedrzejczak",
            "motylek@o2.pl",
            "555777666")

    @Shared
    def employee = new Employee(
            1L,
            "Daniel",
            "Riczardo",
            department
    )

    @Shared
    def car = new Car(
            1L,
            "Skoda",
            "105",
            1988,
            CarStatus.AVAILABLE,
            BigDecimal.valueOf(50L),
            BodyType.SEDAN
    )

    def 'should add rent'() {
        given:
        clientService.addClient(clientRequest)
        def request = new RentCreateRequest(
                clientService.getALlClients().first().getId(),
                employeeService.findAllEmployees().first().getId(),
                car.id,
                "xD")
        service.addRent(request)

        when:
        def result = repository.findAll().first()

        then:
        with(result) {
            id != null
            //client.id == request.clientId
            //employee.id == request.employeeId
            //carId == request.carId
            startDate == LocalDate.now()
            returnDate == null
            charge == null
            comment == request.comment
            rentStatus == RentStatus.ACTIVE
        }
    }
}

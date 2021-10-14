package pl.sda.rentacar.domain.rent

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.sda.rentacar.domain.car.BodyType
import pl.sda.rentacar.domain.car.Car
import pl.sda.rentacar.domain.car.CarCreateRequest
import pl.sda.rentacar.domain.car.CarService
import pl.sda.rentacar.domain.car.CarStatus
import pl.sda.rentacar.domain.client.ClientCreateRequest
import pl.sda.rentacar.domain.client.ClientService
import pl.sda.rentacar.domain.department.Department
import pl.sda.rentacar.domain.department.DepartmentCreateRequest
import pl.sda.rentacar.domain.department.DepartmentService
import pl.sda.rentacar.domain.employee.EmployeeCreateRequest
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
    DepartmentService departmentService

    @Autowired
    EmployeeService employeeService

    @Autowired
    CarService carService

    @Shared
    def departmentRequest = new DepartmentCreateRequest(
            "Radom",
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
    def carRequest = new CarCreateRequest(
            "Mazda",
            "MX-5",
            2019,
            BigDecimal.valueOf(100L),
            BodyType.ROADSTER
    )

    def 'should add rent'() {
        given:
        cleanup()
        def departmentId = givenDepartmentExists(departmentRequest)
        def employeeRequest = new EmployeeCreateRequest(
                "Rimi",
                "Kaikkonen",
                departmentId)
        employeeService.addEmployee(employeeRequest)
        clientService.addClient(clientRequest)
        carService.addCar(carRequest)
        def request = new RentCreateRequest(
                clientService.getALlClients().first().getId(),
                employeeService.findAllEmployees().first().getId(),
                carService.getALlCars().last().getId(),
                "xD")
        service.addRent(request)

        when:
        def result = repository.findAll().last()

        then:
        with(result) {
            id != null
            client.id == request.clientId
            employee.id == request.employeeId
            car.id == request.carId
            startDate == LocalDate.now()
            returnDate == null
            charge == null
            comment == request.comment
            rentStatus == RentStatus.ACTIVE
        }
    }

    private def givenDepartmentExists(DepartmentCreateRequest request) {
        return departmentService.addDepartment(request)
    }

    def cleanup() {
        repository.deleteAll()
    }
}

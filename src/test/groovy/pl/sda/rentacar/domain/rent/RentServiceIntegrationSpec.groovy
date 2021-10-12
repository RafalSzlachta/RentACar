package pl.sda.rentacar.domain.rent

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.sda.rentacar.domain.car.BodyType
import pl.sda.rentacar.domain.car.Car
import pl.sda.rentacar.domain.car.CarCreateRequest
import pl.sda.rentacar.domain.car.CarService
import pl.sda.rentacar.domain.car.CarStatus
import pl.sda.rentacar.domain.client.Client
import pl.sda.rentacar.domain.client.ClientCreateRequest
import pl.sda.rentacar.domain.client.ClientRepository
import pl.sda.rentacar.domain.client.ClientService
import pl.sda.rentacar.domain.department.Department
import pl.sda.rentacar.domain.department.DepartmentCreateRequest
import pl.sda.rentacar.domain.department.DepartmentService
import pl.sda.rentacar.domain.employee.Employee
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
    def department = new Department(
            1L,
            "Lodz",
            [] as Set,
            [] as Set
    )

    /*@Shared
    def departmentRequest = new DepartmentCreateRequest(
            "Radom",
            [] as Set,
            [] as Set
    )*/


    @Shared
    def clientRequest = new ClientCreateRequest(
            "Otylia",
            "Jedrzejczak",
            "motylek@o2.pl",
            "555777666")

    /*@Shared
    def employee = new Employee(
            1L,
            "Daniel",
            "Riczardo",
            department
    )*/

    /*@Shared
    def employeeRequest = new EmployeeCreateRequest(
            "Karol",
            "Eklerek",
            department.getId()
    )*/

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
        def departmentRequest = new DepartmentCreateRequest(
                "Radom",
                [] as Set,
                [] as Set
        )
        def departmentId = givenDepartmentExists(departmentRequest)
        def employeeRequest = new EmployeeCreateRequest("Rimi", "Kaikkonen", departmentId)
        //def employeeId = givenEmployeeExists(employeeRequest)
        employeeService.addEmployee(employeeRequest)
        clientService.addClient(clientRequest)
        carService.addCar(carRequest)
        def request = new RentCreateRequest(
                clientService.getALlClients().first().getId(),
                employeeService.findAllEmployees().first().getId(),
                carService.getALlCars().first().getId(),
                "xD")
        service.addRent(request)

        when:
        def result = repository.findAll().first()

        then:
        with(result) {
            id != null
            client.id == request.clientId
            employee.id == request.employeeId
            //car.id == request.carId
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

    private def givenEmployeeExists(EmployeeCreateRequest request){
        return employeeService.addEmployee(request)
    }

    def cleanup() {
        repository.deleteAll()
    }
}

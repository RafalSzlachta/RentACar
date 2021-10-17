package pl.sda.rentacar.domain.rent

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.sda.rentacar.domain.car.BodyType
import pl.sda.rentacar.domain.car.CarCreateRequest
import pl.sda.rentacar.domain.car.CarRepository
import pl.sda.rentacar.domain.car.CarService
import pl.sda.rentacar.domain.client.ClientCreateRequest
import pl.sda.rentacar.domain.client.ClientRepository
import pl.sda.rentacar.domain.client.ClientService
import pl.sda.rentacar.domain.department.DepartmentCreateRequest
import pl.sda.rentacar.domain.department.DepartmentService
import pl.sda.rentacar.domain.employee.EmployeeCreateRequest
import pl.sda.rentacar.domain.employee.EmployeeRepository
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

    @Autowired
    ClientRepository clientRepository

    @Autowired
    EmployeeRepository employeeRepository

    @Autowired
    CarRepository carRepository

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
    def clientRequest2 = new ClientCreateRequest(
            "Michal",
            "Zewlakow",
            "wybitnyreprezentant@pzpn.pl",
            "555777888")

    def 'should add rent'() {
        given:
        def departmentId = givenDepartmentExists(departmentRequest)
        employeeService.addEmployee(new EmployeeCreateRequest(
                "Rimi",
                "Kaikkonen",
                departmentId))
        clientService.addClient(clientRequest)
        carService.addCar(new CarCreateRequest(
                "Mazda",
                "MX-5",
                2019,
                BigDecimal.valueOf(100L),
                BodyType.ROADSTER,
                departmentId
        ))

        def request = new RentCreateRequest(
                clientService.getAllClients().first().getId(),
                employeeService.findAllEmployees().first().getId(),
                carService.getAllCars().first().getId(),
                "xD")
        service.addRent(request)

        when:
        def result = repository.findAll().first()

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

    def 'should add and find two rents'() {
        given:
        def departmentId = givenDepartmentExists(departmentRequest)
        employeeService.addEmployee(new EmployeeCreateRequest(
                "Karol",
                "Eklerek",
                departmentId))
        employeeService.addEmployee(new EmployeeCreateRequest(
                "Daniel",
                "Riczardo",
                departmentId))
        clientService.addClient(clientRequest)
        clientService.addClient(clientRequest2)
        carService.addCar(new CarCreateRequest(
                "Volvo",
                "C70",
                2012,
                BigDecimal.valueOf(100L),
                BodyType.CONVERTIBLE,
                departmentId))
        carService.addCar(new CarCreateRequest(
                "Citroen",
                "Berlingo",
                2007,
                BigDecimal.valueOf(110L),
                BodyType.PANEL_VAN,
                departmentId))
        def request = new RentCreateRequest(
                clientService.getAllClients().first().getId(),
                employeeService.findAllEmployees().first().getId(),
                carService.getAllCars().first().getId(),
                "xD")
        def request2 = new RentCreateRequest(
                clientService.getAllClients().last().getId(),
                employeeService.findAllEmployees().last().getId(),
                carService.getAllCars().last().getId(),
                "xDD")
        service.addRent(request)
        service.addRent(request2)

        when:
        def rents = service.getALlRents()

        then:
        rents != null
        rents.size() == 2
        with(rents.last()) {
            client.id == request2.clientId
            employee.id == request2.employeeId
            car.id == request2.carId
            startDate == LocalDate.now()
            returnDate == null
            charge == null
            comment == request2.comment
            rentStatus == RentStatus.ACTIVE
        }
    }

    def 'should update rent with given id'() {
        given:
        def departmentId = givenDepartmentExists(departmentRequest)
        clientService.addClient(clientRequest)
        carService.addCar(new CarCreateRequest(
                "Chevrolet",
                "Brookwood",
                1958,
                BigDecimal.valueOf(400),
                BodyType.STATION_WAGON,
                departmentId))
        employeeService.addEmployee(new EmployeeCreateRequest(
                "Karol",
                "Eklerek",
                departmentId))
        def rent = new RentCreateRequest(
                clientService.getAllClients().first().getId(),
                employeeService.findAllEmployees().first().getId(),
                carService.getAllCars().first().getId(),
                "this is comment")
        def rentUpdate = new RentUpdateRequest(
                LocalDate.now().plusDays(2L),
                BigDecimal.valueOf(800),
                "koniec imprezy",
                RentStatus.FINISHED
        )

        service.addRent(rent)
        def id = repository.findAll().last().id

        when:
        service.updateRent(id, rentUpdate)
        def updatedRent = service.getRentById(id)

        then:
        with(updatedRent) {
            startDate == LocalDate.now()
            returnDate == rentUpdate.returnDate
            charge == rentUpdate.charge
            comment == rentUpdate.comment
            rentStatus == rentUpdate.rentStatus
        }
    }

    def 'should remove rent with given id'() {
        given:
        def departmentId = givenDepartmentExists(departmentRequest)
        clientService.addClient(clientRequest2)
        carService.addCar(new CarCreateRequest(
                "Nash",
                "Statesman",
                1951,
                BigDecimal.valueOf(500),
                BodyType.SEDAN,
                departmentId))
        employeeService.addEmployee(new EmployeeCreateRequest(
                "Karol",
                "Eklerek",
                departmentId))
        def rent = new RentCreateRequest(
                clientService.getAllClients().first().getId(),
                employeeService.findAllEmployees().first().getId(),
                carService.getAllCars().first().getId(),
                "this is not comment")
        service.addRent(rent)
        def addedRent = repository.findAll().first()
        def addedRentId = repository.findAll().first().id

        and:
        with(addedRent) {
            id == addedRentId
            client.id == rent.clientId
            employee.id == rent.employeeId
            car.id == rent.carId
            startDate == LocalDate.now()
            returnDate == null
            charge == null
            comment == rent.comment
            rentStatus == RentStatus.ACTIVE
        }

        when:
        service.removeRent(addedRentId)

        then:
        repository.findById(addedRentId).isEmpty()
        repository.findAll().isEmpty()
    }

    private def givenDepartmentExists(DepartmentCreateRequest request) {
        return departmentService.addDepartment(request)
    }

    def cleanup() {
        repository.deleteAll()
        clientRepository.deleteAll()
        employeeRepository.deleteAll()
    }
}

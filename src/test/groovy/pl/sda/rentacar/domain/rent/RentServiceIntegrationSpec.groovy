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
    def clientDaniel = new Client(
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

    @Shared
    def carRequest = new CarCreateRequest(
            "Mazda",
            "MX-5",
            2019,
            BigDecimal.valueOf(100L),
            BodyType.ROADSTER
    )

    @Shared
    def carRequest2 = new CarCreateRequest(
            "Volvo",
            "C70",
            2012,
            BigDecimal.valueOf(100L),
            BodyType.CONVERTIBLE
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
            car.id == request.carId
            startDate == LocalDate.now()
            returnDate == null
            charge == null
            comment == request.comment
            rentStatus == RentStatus.ACTIVE
        }
    }

    def 'should map Rent to RentView'() {
        given:
        cleanup()
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

    def 'should add and find two rents' ()  {
        given:
        cleanup()
        def departmentId = givenDepartmentExists(departmentRequest)
        def employeeRequest = new EmployeeCreateRequest(
                "Karol",
                "Eklerek",
                departmentId)
        def employeeRequest2 = new EmployeeCreateRequest(
                "Daniel",
                "Riczardo",
                departmentId)
        employeeService.addEmployee(employeeRequest)
        employeeService.addEmployee(employeeRequest2)
        clientService.addClient(clientRequest)
        clientService.addClient(clientRequest2)
        carService.addCar(carRequest)
        carService.addCar(carRequest2)
        def request = new RentCreateRequest(
                clientService.getALlClients().first().getId(),
                employeeService.findAllEmployees().first().getId(),
                carService.getALlCars().first().getId(),
                "xD")
        def request2 = new RentCreateRequest(
                clientService.getALlClients().last().getId(),
                employeeService.findAllEmployees().last().getId(),
                carService.getALlCars().last().getId(),
                "xDD")
        service.addRent(request)
        service.addRent(request2)

        when:
        def rents = service.getALlRents()

        then:
        rents !=null
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
        def rent = new RentCreateRequest(
                clientService.getALlClients().first().getId(),
                employeeService.findAllEmployees().first().getId(),
                carService.getALlCars().first().getId(),
                "this is comment")
        def rentUpdate = new RentUpdateRequest(
                LocalDate.now().plusDays(2L),
                BigDecimal.valueOf(200/*carRequest2.getPricePerDay()*2*/),
                "koniec imprezy",
                RentStatus.FINISHED
        )

        service.addRent(rent)
        def id = repository.findAll().last().id

        when:
        service.updateRent(id, rentUpdate)
        def updatedRent = service.getRentById(id)

        then:
        with(updatedRent){
            startDate == LocalDate.now()
            returnDate == rentUpdate.returnDate
            charge == rentUpdate.charge
            comment == rentUpdate.comment
            rentStatus == rentUpdate.rentStatus
        }
    }

    private def givenDepartmentExists(DepartmentCreateRequest request) {
        return departmentService.addDepartment(request)
    }

    def cleanup() {
        repository.deleteAll()
    }
}

package pl.sda.rentacar.develop.DBInitializer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.sda.rentacar.domain.car.CarService;
import pl.sda.rentacar.domain.client.ClientService;
import pl.sda.rentacar.domain.department.DepartmentService;
import pl.sda.rentacar.domain.employee.EmployeeService;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class MockDataInitializer {

    private static Random RANDOM = new Random();

    @Value("${entities.mock.count}")
    private int count;

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final CarService carService;
    private final ClientService clientService;

    @PostConstruct
    void initializeDB() {
        for (int i = 0; i < (count / 2); i++) {
            departmentService.addDepartment(DepartmentMockData.generateMockDepartment());
        }

        for (int i = 0; i < count; i++) {
            employeeService.addEmployee(
                    EmployeeMockData
                    .generateMockEmployee(
                            departmentService
                            .getAllDepartments()
                                    .get(RANDOM.nextInt(count % 2))
                                    .getId()));
            carService.addCar(CarMockData.generateMockCar());
            clientService.addClient(ClientMockData.generateMockClient());
        }
    }
}

package pl.sda.rentacar.develop.DBInitializer;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.sda.rentacar.domain.employee.EmployeeCreateRequest;

import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class EmployeeMockData {

    private static Random RANDOM = new Random();

    private static List<String> FIRST_NAMES = List.of("Franek", "Darek", "Radek", "Marcelina", "Karolina");
    private static List<String> LAST_NAMES = List.of("Żuk", "Chrabąszcz", "Mrówka", "Chrząszcz", "Bąk");

    static EmployeeCreateRequest generateMockEmployee(Long departmentId) {
        return new EmployeeCreateRequest(
                FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size()-1)),
                LAST_NAMES.get(RANDOM.nextInt(LAST_NAMES.size()-1)),
                departmentId);
    }
}

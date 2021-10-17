package pl.sda.rentacar.develop.DBInitializer;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.sda.rentacar.domain.department.DepartmentCreateRequest;

import java.util.List;
import java.util.Random;

import static java.util.Collections.*;

@Component
@Profile("dev")
public class DepartmentMockData {

    private static final Random RANDOM = new Random();

    private final static List<String> ADDRESSES = List.of("Świebodzin", "Toruń", "Radom", "Sosnowiec");

    static DepartmentCreateRequest generateMockDepartment() {
        return new DepartmentCreateRequest(
                ADDRESSES.get(RANDOM.nextInt(ADDRESSES.size()-1)),
                emptySet(),
                emptySet());
    }
}

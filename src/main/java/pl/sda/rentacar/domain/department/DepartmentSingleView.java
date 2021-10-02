package pl.sda.rentacar.domain.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.rentacar.domain.car.Car;
import pl.sda.rentacar.domain.employee.Employee;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSingleView {

    private String address;
    private Set<Employee> employees;
    private Set<Car> cars;
}

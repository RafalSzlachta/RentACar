package pl.sda.rentacar.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.rentacar.domain.department.Department;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeView {

    private String firstName;

    private String lastName;

    private Department department;
}

package pl.sda.rentacar.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.rentacar.domain.department.Department;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public Employee(String firstName, String lastName, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }
}

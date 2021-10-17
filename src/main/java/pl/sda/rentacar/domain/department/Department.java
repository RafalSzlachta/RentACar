package pl.sda.rentacar.domain.department;

import lombok.*;
import pl.sda.rentacar.domain.car.Car;
import pl.sda.rentacar.domain.employee.Employee;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@Setter
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String address;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<Employee> employees;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "DEPARTMENTS_CARS",
        joinColumns = @JoinColumn(name = "department_id"),
        inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private Set<Car> cars;
}

package pl.sda.rentacar.domain.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sda.rentacar.domain.car.Car;
import pl.sda.rentacar.domain.employee.Employee;

import javax.persistence.*;
import java.util.Collections;
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
    private Set<Employee> employees = Collections.emptySet();

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Car> cars = Collections.emptySet();
}

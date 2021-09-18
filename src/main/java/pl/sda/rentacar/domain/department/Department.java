package pl.sda.rentacar.domain.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.rentacar.domain.car.Car;
import pl.sda.rentacar.domain.employee.Employee;

import javax.persistence.*;


import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name= "Departments")
public class Department {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String address;
    @OneToMany
    private List<Employee> employees;
    @OneToMany
    private List<Car> cars;
}

package pl.sda.rentacar.domain.rent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import pl.sda.rentacar.domain.car.Car;
import pl.sda.rentacar.domain.client.Client;
import pl.sda.rentacar.domain.employee.Employee;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "rents")
public class Rent {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToOne
    private Car car;

    private LocalDate startDate = LocalDate.now();

    private LocalDate returnDate;

    private BigDecimal charge;

    private String comment;

    private RentStatus rentStatus = RentStatus.ACTIVE;

    public Rent(Client client, Employee employee, Car car,  String comment) {
        this.client = client;
        this.employee = employee;
        this.car = car;
        this.comment = comment;
    }
}
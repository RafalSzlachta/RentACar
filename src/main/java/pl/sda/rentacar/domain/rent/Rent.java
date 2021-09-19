package pl.sda.rentacar.domain.rent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @OneToOne
    private Client client;

    @OneToOne
    private Employee employee;

    @OneToOne
    private Car car;

    private LocalDate startDate;

    private LocalDate returnDate;

    private BigDecimal charge;

    private String comment;

    private RentStatus rentStatus;
}
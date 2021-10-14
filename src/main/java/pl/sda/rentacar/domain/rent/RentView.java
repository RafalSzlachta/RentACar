package pl.sda.rentacar.domain.rent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.rentacar.domain.car.Car;
import pl.sda.rentacar.domain.client.Client;
import pl.sda.rentacar.domain.employee.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentView {

    private Client client;
    private Employee employee;
    private Car car;
    private LocalDate startDate;
    private LocalDate returnDate;
    private BigDecimal charge;
    private String comment;
    private RentStatus rentStatus;
}


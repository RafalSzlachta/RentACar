package pl.sda.rentacar.domain.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@RequiredArgsConstructor
//@NoArgsConstructor
@Data
@Table(name = "Cars")

public class Car {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String make;
    private String model;
    private Integer productionYear;
    private CarStatus status = CarStatus.AVAILABLE;
    private BigDecimal pricePerDay;
    private BodyType bodyType;
}


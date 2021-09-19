package pl.sda.rentacar.domain.car;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Cars")

public class Car {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String make;
    private String model;
    private String bodytype;
    private Integer productionYear;
    private Snippet.Status status;
    private BigDecimal pricePerDay;
    private BodyType bodyType;
}

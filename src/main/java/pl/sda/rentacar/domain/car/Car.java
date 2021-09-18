package pl.sda.rentacar.domain.car;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table (name= "Cars")

public class Car {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String producer;
    private String model;
    private String bodytype;
    private Integer yearOfProduce;
    private Snippet.Status status;
    private Integer pricePerDay;
}

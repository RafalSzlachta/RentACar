package pl.sda.rentacar.domain.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarView {

    private Long id;
    private String make;
    private String model;
    private Integer productionYear;
    private CarStatus status;
    private BigDecimal pricePerDay;
    private BodyType bodyType;
}
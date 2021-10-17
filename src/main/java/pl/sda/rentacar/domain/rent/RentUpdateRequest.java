package pl.sda.rentacar.domain.rent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.rentacar.domain.client.Client;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentUpdateRequest {

    private LocalDate returnDate;
    private BigDecimal charge;
    private String comment;
    private RentStatus rentStatus;
}

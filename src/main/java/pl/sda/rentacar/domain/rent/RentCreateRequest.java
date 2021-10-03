package pl.sda.rentacar.domain.rent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentCreateRequest {

    private Long clientId;

    private Long employeeId;

    private Long carId;

    private String comment;
}

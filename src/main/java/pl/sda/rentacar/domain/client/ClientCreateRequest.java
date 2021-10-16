package pl.sda.rentacar.domain.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}

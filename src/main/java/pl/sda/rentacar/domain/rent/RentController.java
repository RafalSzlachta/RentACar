package pl.sda.rentacar.domain.rent;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class RentController {

    private final RentService service;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<RentView> getAllRents(){
        return service.getALlRents();
    }
}

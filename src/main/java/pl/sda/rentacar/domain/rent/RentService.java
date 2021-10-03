package pl.sda.rentacar.domain.rent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.rentacar.domain.car.Car;
import pl.sda.rentacar.domain.client.ClientService;
import pl.sda.rentacar.domain.employee.EmployeeService;

@Service
@RequiredArgsConstructor
public class RentService {
    private final RentRepository repository;

    private final ClientService clientService;
    private final EmployeeService employeeService;

    public void addRent(RentCreateRequest request) {
        Rent rent = new Rent(
                clientService.findClientById(request.getClientId()),
                employeeService.getEmployeeById(request.getEmployeeId()),
                new Car(),
                request.getComment()
                );
        repository.save(rent);
    }
}

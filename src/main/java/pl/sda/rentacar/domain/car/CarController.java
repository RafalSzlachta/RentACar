package pl.sda.rentacar.domain.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CarController {

    private final CarService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCar(@RequestBody CarCreateRequest request) {
        service.addCar(request);
    }

    @GetMapping
    public List<CarView> getALlCars() {
        return service.getAllCars();
    }

    @GetMapping("/{id")
    public CarView getCarById(@PathVariable Long id) {
        return service.getCarById(id);
    }

    @PutMapping("/{id}")
    public void updateCar(@PathVariable Long id, @RequestBody CarCreateRequest request) {
        service.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCarById(@PathVariable Long id) {
        service.removeCar(id);
    }
}

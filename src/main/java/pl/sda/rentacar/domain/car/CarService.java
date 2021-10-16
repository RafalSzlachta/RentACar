package pl.sda.rentacar.domain.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static pl.sda.rentacar.domain.car.CarMapper.*;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository repository;

    public void addCar(CarCreateRequest request) {
        Car car = MAPPER.mapToCar(request);
        repository.save(car);
    }

    List<CarView> getALlCars() {
        return repository
                .findAll()
                .stream()
                .map(MAPPER::mapToCarView)
                .collect(Collectors.toList());
    }

    CarView getCarById(Long id) {
        return MAPPER.mapToCarView(findCarById(id));
    }

    Car findCarById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    void updateCar(Long id, CarCreateRequest request) {
        Car car = findCarById(id);
        car.setMake(request.getMake());
        car.setModel(request.getModel());
        car.setProductionYear(request.getProductionYear());
        car.setPricePerDay(request.getPricePerDay());
        car.setBodyType(request.getBodyType());
        repository.save(car);
    }

    public void removeCar(Long id) {
        repository.delete(findCarById(id));
    }
}


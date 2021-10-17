package pl.sda.rentacar.domain.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.rentacar.domain.department.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

import static pl.sda.rentacar.domain.car.CarMapper.MAPPER;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository repository;
    private final DepartmentService departmentService;

    @Transactional
    // TODO - required update of integration tests
    public void addCar(CarCreateRequest request) {
        Car car = MAPPER.mapToCar(request);
        departmentService.addCar(request.getDepartmentId(), car);
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


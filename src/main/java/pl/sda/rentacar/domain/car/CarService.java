package pl.sda.rentacar.domain.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.rentacar.domain.department.Department;
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
        Department department = departmentService.findDepartmentById(request.getDepartmentId());
        car.setDepartment(department);
        repository.save(car);
    }

    public List<CarView> getAllCars() {
        return repository
            .findAll()
            .stream()
            .map(MAPPER::mapToCarView)
                .collect(Collectors.toList());
    }

    public CarView getCarById(Long id) {
        return MAPPER.mapToCarView(findCarById(id));
    }

    public Car findCarById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    public void updateCar(Long id, CarCreateRequest request) {
        Car car = findCarById(id);
        car.setMake(request.getMake());
        car.setModel(request.getModel());
        car.setProductionYear(request.getProductionYear());
        car.setPricePerDay(request.getPricePerDay());
        car.setBodyType(request.getBodyType());
        repository.save(car);
    }

    public void removeCar(Long id) {
        Car car = findCarById(id);
        repository.delete(car);
    }
}


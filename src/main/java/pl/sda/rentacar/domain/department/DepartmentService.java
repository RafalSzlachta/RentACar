package pl.sda.rentacar.domain.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.rentacar.domain.car.Car;

import java.util.List;
import java.util.stream.Collectors;

import static pl.sda.rentacar.domain.department.DepartmentMapper.MAPPER;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public Long addDepartment(DepartmentCreateRequest departmentCreateRequest) {
        Department department = MAPPER.toDepartment(departmentCreateRequest);
        return repository.save(department).getId();
    }

    public DepartmentSingleView getDepartmentById(Long id) {
        Department department = findDepartmentById(id);
        return MAPPER.toDepartmentSingleView(department);
    }

    public Department findDepartmentById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

    }

    public List<DepartmentListView> getAllDepartments() {
        return repository
                .findAll()
                .stream()
                .map(MAPPER::toDepartmentListView)
                .collect(Collectors.toList());
    }

    public void updateDepartment(Long id, DepartmentCreateRequest request) {
        Department department = findDepartmentById(id);
        department.setAddress(request.getAddress());
        department.setEmployees(request.getEmployees());
        department.setCars(request.getCars());
        repository.save(department);
    }

    public void removeDepartment(Long id) {
        repository.delete(findDepartmentById(id));
    }

    public void addCar(Long departmentId, Car car) {
        Department department = findDepartmentById(departmentId);
        department.getCars().add(car);
    }

}

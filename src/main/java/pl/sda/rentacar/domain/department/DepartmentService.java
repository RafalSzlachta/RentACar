package pl.sda.rentacar.domain.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static pl.sda.rentacar.domain.department.DepartmentMapper.MAPPER;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public void addDepartment(DepartmentCreateRequest departmentCreateRequest) {
        Department department = MAPPER.toDepartment(departmentCreateRequest);
        repository.save(department);
    }
}

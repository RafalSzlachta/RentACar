package pl.sda.rentacar.domain.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.rentacar.domain.department.Department;
import pl.sda.rentacar.domain.department.DepartmentService;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static pl.sda.rentacar.domain.employee.EmployeeMapper.MAPPER;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final DepartmentService departmentService;
    private final EmployeeRepository employeeRepository;

    public void addEmployee(EmployeeCreateRequest request) {
        Employee employee = MAPPER.mapToEmployee(request);
        Department department = departmentService.findDepartmentById(request.getDepartmentId());
        employee.setDepartment(department);
        employeeRepository.save(employee);
    }

    public List<EmployeeView> findAllEmployees() {
        return employeeRepository
                .findAll()
                .stream()
                .map(MAPPER::mapToEmployeeView)
                .collect(toList());
    }

    public EmployeeView findEmployeeById(Long id) {
        return MAPPER.mapToEmployeeView(getEmployeeById(id));
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Transactional
    public void deleteEmployeeById(Long id) {
        Employee employee = getEmployeeById(id);
        employee.getDepartment().getEmployees().remove(employee);
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void updateEmployee(Long id, EmployeeCreateRequest request) {
        Employee employee = getEmployeeById(id);
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        Department department = departmentService.findDepartmentById(request.getDepartmentId());
        employee.setDepartment(department);
    }
}

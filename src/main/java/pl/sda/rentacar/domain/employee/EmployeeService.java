package pl.sda.rentacar.domain.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static pl.sda.rentacar.domain.employee.EmployeeMapper.MAPPER;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public void addEmployee(EmployeeCreateRequest request){
        Employee employee = MAPPER.mapToEmployee(request);
        employeeRepository.save(employee);
    }

    public List<EmployeeView> findAllEmployees(){
        return employeeRepository
                .findAll()
                .stream()
                .map(MAPPER::mapToEmployeeView).collect(toList());
    }

    public EmployeeView findEmployeeById(Long id) {
        return employeeRepository
                .findById(id).map(MAPPER::mapToEmployeeView).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository
                .findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.delete(getEmployeeById(id));
    }

    public void updateEmployee(Long id, EmployeeCreateRequest request){
        Employee employee = getEmployeeById(id);
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setDepartment(request.getDepartment());
        employeeRepository.save(employee);
    }
}

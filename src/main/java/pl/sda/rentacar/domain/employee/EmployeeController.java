package pl.sda.rentacar.domain.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody EmployeeCreateRequest request){
        service.addEmployee(request);
    }

    @GetMapping
    public List<EmployeeView> findAllEmployees() {
        return service.findAllEmployees();
    }

    @GetMapping("/{id")
    public EmployeeView findEmployeeById(@PathVariable Long id) {
        return service.findEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Long id) {
        service.deleteEmployeeById(id);
    }

    @PutMapping({"/id"})
    public void updateEmployee(@PathVariable Long id, @RequestBody EmployeeCreateRequest request){
        service.updateEmployee(id, request);
    }
}

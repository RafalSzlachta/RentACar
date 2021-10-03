package pl.sda.rentacar.domain.department;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
class DepartmentController {

    private final DepartmentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addDepartment(@RequestBody DepartmentCreateRequest request) {
        service.addDepartment(request);
    }

    @GetMapping("/{id}")
    public DepartmentSingleView getClientById(@PathVariable Long id) {
        return service.getDepartmentById(id);
    }

    @GetMapping
    public List<DepartmentListView> getALlClients() {
        return service.getAllDepartments();
    }

    @PutMapping("/{id}")
    public void updateDepartment(@PathVariable Long id, @RequestBody DepartmentCreateRequest request) {
        service.updateDepartment(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDepartmentById(@PathVariable Long id) {
        service.removeDepartment(id);
    }

}

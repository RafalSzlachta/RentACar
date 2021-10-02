package pl.sda.rentacar.domain.employee;


public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Employee with id " + id + " does not exist");
    }
}

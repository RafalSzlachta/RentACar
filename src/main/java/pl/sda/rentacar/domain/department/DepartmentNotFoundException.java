package pl.sda.rentacar.domain.department;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long id) {
        super("department with id: " + id + " doesn't exists");
    }
}

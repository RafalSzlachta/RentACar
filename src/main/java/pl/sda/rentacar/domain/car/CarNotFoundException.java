package pl.sda.rentacar.domain.car;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(Long id) {
        super("car with id: " + id + " doesn't exsists");
    }
}

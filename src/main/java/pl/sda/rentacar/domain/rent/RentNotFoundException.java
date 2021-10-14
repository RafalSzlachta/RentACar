package pl.sda.rentacar.domain.rent;

public class RentNotFoundException extends RuntimeException {
    public RentNotFoundException(Long id) {
        super ("Rent with id " + id + " does not exist");
    }
}

package pl.sda.rentacar.domain.client;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("client with id: " + id + " doesn't exsists");
    }
}

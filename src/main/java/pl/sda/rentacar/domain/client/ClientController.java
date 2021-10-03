package pl.sda.rentacar.domain.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addClient(@RequestBody ClientCreateRequest request) {
        service.addClient(request);
    }

    @GetMapping
    public List<ClientView> getALlClients() {
        return service.getALlClients();
    }

    @GetMapping("/{id}")
    public ClientView getClientById(@PathVariable Long id) {
        return service.getClientById(id);
    }

    @PutMapping("/{id}")
    public void updateClient(@PathVariable Long id, @RequestBody ClientCreateRequest request) {
        service.updateClient(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeClientById(@PathVariable Long id) {
        service.removeClient(id);
    }
}

package pl.sda.rentacar.domain.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static pl.sda.rentacar.domain.client.ClientMapper.*;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public void addClient(ClientCreateRequest request) {
        Client client = MAPPER.mapToClient(request);
        repository.save(client);
    }

    List<ClientView> getALlClients() {
        return repository
                .findAll()
                .stream()
                .map(MAPPER::mapToClientView)
                .collect(Collectors.toList());
    }

    ClientView getClientById(Long id) {
        return MAPPER.mapToClientView(findClientById(id));
    }

    Client findClientById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    void updateClient(Long id, ClientCreateRequest request) {
        Client client = findClientById(id);
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setPhoneNumber(request.getPhoneNumber());
        repository.save(client);
    }

    public void removeClient(Long id) {
        repository.delete(findClientById(id));
    }

}

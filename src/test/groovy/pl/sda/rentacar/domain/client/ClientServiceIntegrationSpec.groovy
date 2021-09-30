package pl.sda.rentacar.domain.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification


@ContextConfiguration
@SpringBootTest
class ClientServiceIntegrationSpec extends Specification {

    @Autowired
    private ClientRepository repository

    @Autowired
    private ClientService service

    def id = 1L
    def client = new Client(
            id,
            "Beata",
            "Kozidrak",
            "beata@kozidrak.pl",
            "222555333")

    void initialRepositoryWithOneClient() {
        repository.save(client)
    }

    def 'ShouldAddTwoClientsAndFindTwoClients'() {
        given:
        initialRepositoryWithOneClient()
        def secondClient = new ClientCreateRequest(
                "Kamil",
                "Durczok",
                "kamil@durczok.pl",
                "111444222")
        when:
        service.addClient(secondClient)
        and:
        def clients = service.getALlClients()
        then:
        clients != null
        clients.size() == 2
        with(clients.last()) {
            firstName == secondClient.firstName
            lastName == secondClient.lastName
            email == secondClient.email
            phoneNumber == secondClient.phoneNumber
        }
    }

    def 'should return client by given id if exist'() {
        given:
        initialRepositoryWithOneClient()

        when:
        def result = service.getClientById(id)

        then:
        with(result) {
            id == client.id
            firstName == client.firstName
            lastName == client.lastName
            email == client.email
            phoneNumber == client.phoneNumber
        }
    }

    def 'should throw ClientNotFoundException'() {
        given:
        repository.findById(_ as Long) >> Optional.empty()

        when:
        service.getClientById(10L)

        then:
        def e = thrown(ClientNotFoundException)
        e.message == "client with id: 10 doesn't exsists"
    }

    def 'should update client with given id'() {
        given:
        initialRepositoryWithOneClient()

        def requestUpdate = new ClientCreateRequest(
                "Edyta",
                "Górniak",
                "edyta@górniak.pl",
                "666999666")

        when:
        service.updateClient(id, requestUpdate)
        def updatedClient = service.getClientById(id)
        then:
        with(updatedClient) {
            firstName == requestUpdate.firstName
            lastName == requestUpdate.lastName
            email == requestUpdate.email
            phoneNumber == requestUpdate.phoneNumber
        }
    }

    def 'should remove client with given id'() {
        given:
        initialRepositoryWithOneClient()

        when:
        service.removeClient(id)
        def result = service.getALlClients()

        then:
        result.isEmpty()
    }
}
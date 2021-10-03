package pl.sda.rentacar.domain.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class ClientServiceIntegrationSpec extends Specification {

    @Autowired
    private ClientRepository repository

    @Autowired
    private ClientService service

    def cleanup() {
        repository.deleteAll()
    }

    @Shared
    def client = new ClientCreateRequest(
            "Beata",
            "Kozidrak",
            "beata@kozidrak.pl",
            "222555333")

    def 'should add client'() {
        given:
        service.addClient(client)

        when:
        def result = repository.findAll()

        then:
        with(result.first()) {
            id != null
            firstName == client.firstName
            lastName == client.lastName
            email == client.email
            phoneNumber == client.phoneNumber
        }
    }

    def 'should return client by given id if exist'() {
        given:
        service.addClient(client)
        def all = repository.findAll()
        def id = all.first().id

        when:
        def result = service.getClientById(id)

        then:
        with(result) {
            firstName == client.firstName
            lastName == client.lastName
            email == client.email
            phoneNumber == client.phoneNumber
        }
    }

    def 'should add two clients and find two clients'() {
        given:
        def secondClient = new ClientCreateRequest(
                "Kamil",
                "Durczok",
                "kamil@durczok.pl",
                "111444222")

        when:
        service.addClient(client)
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

    def 'should throw ClientNotFoundException'() {
        when:
        service.getClientById(10L)

        then:
        def e = thrown(ClientNotFoundException)
        e.message == "client with id: 10 doesn't exsists"
    }

    def 'should update client with given id'() {
        given:
        def requestUpdate = new ClientCreateRequest(
                "Edyta",
                "Górniak",
                "edyta@górniak.pl",
                "666999666")

        and:
        service.addClient(client)
        def id = repository.findAll().first().id

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
        service.addClient(client)
        def id = repository.findAll().first().id

        when:
        service.removeClient(id)

        then:
        repository.findAll().isEmpty()
    }

}
package pl.sda.rentacar.domain.client

import spock.lang.Specification


class ClientMapperSpec extends Specification{

    def 'should map ClientCreateRequest to Client'(){
        given:
            def request = new ClientCreateRequest(
                    "Marcin",
                    "Żewłakow",
                    "marcin@żewłakow.pl",
                    "123456789")
        when:
            def result = ClientMapper.MAPPER.mapToClient(request)
        then:
        result.id == null
        result.firstName == request.firstName
        result.lastName == request.lastName
        result.phoneNumber == request.phoneNumber
    }
}

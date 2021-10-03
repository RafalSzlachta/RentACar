package pl.sda.rentacar.domain.car

import spock.lang.Specification

class CarMapperSpec extends Specification {

    def 'should map CarCreateRequest to Car'() {
        given:
        def request = new CarCreateRequest(
                "Fiat",
                "Panda",
                1234,
                BigDecimal.TEN,
                BodyType.HATCHBACK
        )
        when:
        def result = CarMapper.MAPPER.mapToCar(request)
        then:
        result.make == request.make
        result.model == request.model
        result.productionYear == request.productionYear
        result.pricePerDay == request.pricePerDay
        request.bodyType == request.bodyType

    }
}
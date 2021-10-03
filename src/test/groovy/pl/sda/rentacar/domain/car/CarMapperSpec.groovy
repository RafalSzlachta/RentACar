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
        with(result) {
            make == make
            model == model
            productionYear == productionYear
            pricePerDay == pricePerDay
            bodyType == bodyType
        }
    }
}
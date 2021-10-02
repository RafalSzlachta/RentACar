package pl.sda.rentacar.domain.car;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.sda.rentacar.domain.car.Car;
import pl.sda.rentacar.domain.car.CarCreateRequest;
import pl.sda.rentacar.domain.car.CarView;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
interface CarMapper {

    pl.sda.rentacar.domain.car.CarMapper MAPPER = getMapper(pl.sda.rentacar.domain.car.CarMapper.class);

    @Mapping(target = "id", ignore = true)
    Car mapToCar(CarCreateRequest request);

    CarView mapToCarView(Car client);
}

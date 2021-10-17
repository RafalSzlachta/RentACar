package pl.sda.rentacar.domain.car;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
interface CarMapper {

    CarMapper MAPPER = getMapper(pl.sda.rentacar.domain.car.CarMapper.class);

    @Mapping(target = "id", ignore = true)
    Car mapToCar(CarCreateRequest request);

    CarView mapToCarView(Car car);
}
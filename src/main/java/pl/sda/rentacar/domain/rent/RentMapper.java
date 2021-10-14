package pl.sda.rentacar.domain.rent;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface RentMapper {

    RentMapper MAPPER = getMapper(RentMapper.class);

    /*@Mapping(target = "id", ignore = true)
    Rent mapToRent(RentCreateRequest request);*/

    RentView mapToRentView(Rent rent);
}

package pl.sda.rentacar.domain.rent;

import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface RentMapper {

    RentMapper MAPPER = getMapper(RentMapper.class);

    RentView mapToRentView(Rent rent);
}

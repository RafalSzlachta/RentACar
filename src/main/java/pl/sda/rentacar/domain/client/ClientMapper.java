package pl.sda.rentacar.domain.client;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
interface ClientMapper {

    ClientMapper MAPPER = getMapper(ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    Client mapToClient(ClientCreateRequest request);

    ClientView mapToClientView(Client client);
}



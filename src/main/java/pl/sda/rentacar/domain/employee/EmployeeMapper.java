package pl.sda.rentacar.domain.employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
interface EmployeeMapper {

    EmployeeMapper MAPPER = getMapper(EmployeeMapper.class);

    @Mapping(target = "id", ignore = true)

    Employee mapToEmployee(EmployeeCreateRequest request);

    EmployeeView mapToEmployeeView(Employee employee);
}

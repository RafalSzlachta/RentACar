package pl.sda.rentacar.domain.department;

import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
interface DepartmentMapper {

    DepartmentMapper MAPPER = getMapper(DepartmentMapper.class);

    DepartmentSingleView toDepartmentSingleView(Department department);

    DepartmentListView toDepartmentListView(Department department);

    Department toDepartment(DepartmentCreateRequest departmentCreateRequest);
}

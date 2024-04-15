package br.unesp.parking.manager.api.web.dto.mapper;

import br.unesp.parking.manager.api.entity.Employee;
import br.unesp.parking.manager.api.web.dto.CreateEmployeeDto;
import br.unesp.parking.manager.api.web.dto.EmployeeResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMapper {

    public static Employee toEmployee(CreateEmployeeDto dto) {
        return new ModelMapper().map(dto, Employee.class);
    }

    public static EmployeeResponseDto toDto(Employee employee) {
        return new ModelMapper().map(employee, EmployeeResponseDto.class);
    }
}

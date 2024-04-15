package br.unesp.parking.manager.api.web.dto.mapper;

import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.web.dto.CustomerResponseDto;
import br.unesp.parking.manager.api.web.dto.CreateCustomerDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerMapper {

    public static Customer toCustomer(CreateCustomerDto dto) {
        return new ModelMapper().map(dto, Customer.class);
    }

    public static CustomerResponseDto toDto(Customer customer) {
        return new ModelMapper().map(customer, CustomerResponseDto.class);
    }
}

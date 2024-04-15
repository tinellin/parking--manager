package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.repository.projection.CustomerProjection;
import br.unesp.parking.manager.api.service.CarInfoService;
import br.unesp.parking.manager.api.service.CustomerService;
import br.unesp.parking.manager.api.web.dto.*;
import br.unesp.parking.manager.api.web.dto.mapper.CarInfoMapper;
import br.unesp.parking.manager.api.web.dto.mapper.CustomerMapper;
import br.unesp.parking.manager.api.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/consumers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CarInfoService carInfoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto create(@RequestBody @Valid CreateCustomerDto dto) {
        Customer customer = CustomerMapper.toCustomer(dto);
        customerService.save(customer);

        return CustomerMapper.toDto(customer);
    }

    @GetMapping("/{id}")
    public CustomerResponseDto getById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        return CustomerMapper.toDto(customer);
    }

    @GetMapping()
    public PageableDto getAll(Pageable pageable) {
        Page<CustomerProjection> customers = customerService.findAll(pageable);
        return PageableMapper.toDto(customers);
    }

    @PostMapping("/car/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CarInfoResponseDto mapNewCar(@PathVariable Long id, @RequestBody @Valid CreateCarInfoDto dto) {
        CarInfo carInfo = carInfoService.create(CarInfoMapper.toCarInfo(dto), id);
        return CarInfoMapper.toDto(carInfo);
    }
}

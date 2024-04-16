package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.jwt.JwtUserDetails;
import br.unesp.parking.manager.api.repository.projection.CustomerProjection;
import br.unesp.parking.manager.api.service.CarInfoService;
import br.unesp.parking.manager.api.service.CustomerService;
import br.unesp.parking.manager.api.service.UserService;
import br.unesp.parking.manager.api.web.dto.*;
import br.unesp.parking.manager.api.web.dto.mapper.CarInfoMapper;
import br.unesp.parking.manager.api.web.dto.mapper.CustomerMapper;
import br.unesp.parking.manager.api.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final UserService userService;
    private final CarInfoService carInfoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto create(@RequestBody @Valid CreateCustomerDto dto,  @AuthenticationPrincipal JwtUserDetails userDetails) {
        Customer customer = CustomerMapper.toCustomer(dto);
        customer.setUser(userService.getById(userDetails.getId()));
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

    @GetMapping("/details")
    public CustomerResponseDto getDetails(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Customer customer = customerService.findUserByIdAuthenticated(userDetails.getId());
        return CustomerMapper.toDto(customer);
    }

    @PostMapping("/car")
    @ResponseStatus(HttpStatus.CREATED)
    public CarInfoResponseDto mapNewCar(@AuthenticationPrincipal JwtUserDetails user, @RequestBody @Valid CreateCarInfoDto dto) {
        Customer customer = customerService.findUserByIdAuthenticated(user.getId());
        CarInfo carInfo = carInfoService.create(customer, CarInfoMapper.toCarInfo(dto));
        return CarInfoMapper.toDto(carInfo);
    }

    @DeleteMapping("/car")
    public void deleteCar(@RequestParam(name = "id") String licensePlate) {
        carInfoService.delete(licensePlate);
    }
}

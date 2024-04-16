package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.entity.User;
import br.unesp.parking.manager.api.service.UserService;
import br.unesp.parking.manager.api.web.dto.UserCreateDto;
import br.unesp.parking.manager.api.web.dto.UserPasswordDto;
import br.unesp.parking.manager.api.web.dto.UserResponseDto;
import br.unesp.parking.manager.api.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody() UserCreateDto createDto) {
        User response = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(response));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable() Long id) {
        User user = userService.getById((id));
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable() Long id, @Valid @RequestBody() UserPasswordDto dto) {
        userService.updatePassword(id, dto.getPassword(), dto.getNewPassword(), dto.getConfirmNewPassword());
        return ResponseEntity.noContent().build();
    }

}

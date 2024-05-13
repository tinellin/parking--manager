package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.entity.User;
import br.unesp.parking.manager.api.jwt.JwtUserDetails;
import br.unesp.parking.manager.api.service.UserService;
import br.unesp.parking.manager.api.web.dto.UserCreateDto;
import br.unesp.parking.manager.api.web.dto.UserPasswordDto;
import br.unesp.parking.manager.api.web.dto.UserResponseDto;
import br.unesp.parking.manager.api.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/details")
    public ResponseEntity<UserResponseDto> getUserDetails(@AuthenticationPrincipal JwtUserDetails user) {
        User response = userService.getById(user.getId());
        return ResponseEntity.ok(UserMapper.toDto(response));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENT') AND #id == authentication.principal.id )")
    public ResponseEntity<UserResponseDto> getById(@PathVariable() Long id) {
        User user = userService.getById((id));
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') AND ( #id == authentication.principal.id )")
    public ResponseEntity<Void> updatePassword(@PathVariable() Long id, @Valid @RequestBody() UserPasswordDto dto) {
        userService.updatePassword(id, dto.getPassword(), dto.getNewPassword(), dto.getConfirmNewPassword());
        return ResponseEntity.noContent().build();
    }

}


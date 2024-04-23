package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.jwt.JwtToken;
import br.unesp.parking.manager.api.jwt.JwtUserDetailsService;
import br.unesp.parking.manager.api.web.dto.UserLoginDto;
import br.unesp.parking.manager.api.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto dto, HttpServletRequest req) {
        log.info("Processo de autenticação de login com {}", dto.getUsername());

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            authManager.authenticate(authToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());

            return ResponseEntity.ok(token);

        } catch (AuthenticationException ex) {
            log.warn("Credenciais incorretas para o usuário {}", dto.getUsername());
        }

        return ResponseEntity.badRequest().body(new ErrorMessage(req, HttpStatus.BAD_REQUEST, "Credenciais incorretas."));
    }
}
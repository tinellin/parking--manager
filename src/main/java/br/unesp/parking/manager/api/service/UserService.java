package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.User;
import br.unesp.parking.manager.api.exception.EntityNotFoundException;
import br.unesp.parking.manager.api.exception.PasswordInvalidException;
import br.unesp.parking.manager.api.exception.UsernameUniqueViolationException;
import br.unesp.parking.manager.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Usuário {%s} já registrado.", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com id=%s não encontrado.", id))
        );
    }

    @Transactional()
    public User updatePassword(Long id, String password, String newPassword, String confirmNewPassword) {

        if (!newPassword.equals(confirmNewPassword)) throw new PasswordInvalidException("As senhas não são iguais.");

        User user = getById(id);

        if(!passwordEncoder.matches(password, user.getPassword())) throw new PasswordInvalidException("Sua senha não confere.");

        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário '%s' não encontrado.", username))
        );
    }
    @Transactional(readOnly = true)
    public User.Role getRoleById(String username) {
        return userRepository.findRoleByUsername(username);
    }
}

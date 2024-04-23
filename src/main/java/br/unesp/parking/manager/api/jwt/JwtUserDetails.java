package br.unesp.parking.manager.api.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/* Criar objeto de autenticação JWT gerenciado pelo Spring Security */
public class JwtUserDetails extends User {
    private final br.unesp.parking.manager.api.entity.User user;

    public JwtUserDetails(br.unesp.parking.manager.api.entity.User  user) {
        /* Authority List vai permitir a implementação posteriormente do controle de acesso */
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getRole() {
        return this.user.getRole().name();
    }
}

package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.repository.projection.CustomerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c.id AS id, c.name AS name, c.birthday AS birthday, c.cpf AS cpf, c.balance AS balance, c.occupation AS occupation, c.user.id AS userId, c.user.username AS username, c.user.role AS role FROM Customer c")
    Page<CustomerProjection> findAllPageable(Pageable pageable);

    Customer findByUserId(Long id);

    Optional<Customer> findByCpf(String cpf);
}

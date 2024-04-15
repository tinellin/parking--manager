package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.repository.projection.CustomerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c")
    Page<CustomerProjection> findAllPageable(Pageable pageable);

    Customer findByUserId(Long id);

    Optional<Customer> findByCpf(String cpf);
}

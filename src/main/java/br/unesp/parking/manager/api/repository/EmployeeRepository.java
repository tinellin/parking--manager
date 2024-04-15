package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.Employee;
import br.unesp.parking.manager.api.repository.projection.EmployeeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e")
    Page<EmployeeProjection> findAllPageable(Pageable pageable);
}

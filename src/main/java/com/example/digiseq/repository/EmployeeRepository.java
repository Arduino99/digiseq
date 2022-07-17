package com.example.digiseq.repository;

import com.example.digiseq.domain.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findBy(Employee employee, Sort sort);

    List<Employee> findEmployeesByOrganisationId(Long id);
}

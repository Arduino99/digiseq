package com.example.digiseq.service;

import com.example.digiseq.domain.Employee;
import com.example.digiseq.exceptions.EmployeeNotFoundException;
import com.example.digiseq.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //gets all employees
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    //gets all employees from given organisation
    public List<Employee> getAllEmployeesByOrganisationId(Long id) {
        return (List<Employee>) employeeRepository.findEmployeesByOrganisationId(id);
    }

    // save an employee
    public void saveEmployee(Employee emp) {
        employeeRepository.save(emp);
    }

    //	get employee by id
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    //delete employee by ID
    public void deleteEmployeeById(Long id){
        employeeRepository.deleteById(id);
    }


    //edit employee

}

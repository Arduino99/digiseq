package com.example.digiseq.controller;

import com.example.digiseq.domain.Employee;
import com.example.digiseq.domain.Organisation;
import com.example.digiseq.service.EmployeeService;
import com.example.digiseq.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return "index";
    }

    //	showNewEmployeeForm
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "newEmployee";
    }

    //	add an employee
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/updateEmployee/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "updateEmployee";
    }

    @GetMapping("/getEmployeesByOrganisation/{id}")
    public String showEmployeesByOrganisationId(@PathVariable Long id, Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployeesByOrganisationId(id));
        return "index";
    }

    //	delete the employee by id
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployeeById(@PathVariable Long id, Model model) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }
}

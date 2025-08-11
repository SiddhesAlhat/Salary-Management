package com.one.project.MyController;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.one.project.DAO.EmployeeRepo;
import com.one.project.Employ.Employee;

@Controller
public class MyController {

    @Autowired
    private EmployeeRepo repo;

    public List<Employee> getEmployeeData() {
        return repo.findAll();
    }

    @GetMapping("/index")
    public String listEmployee(Model model) {
        model.addAttribute("employees", getEmployeeData());
        model.addAttribute("employees1", new Employee());
        return "employee";//despaing on the page....
    }

    @PostMapping("/index1")
    public String addEmployee(@ModelAttribute("employees1") Employee employee) {
        repo.save(employee);
        return "redirect:/index";
    }

    public Employee getEmployeeId(int id) {
        return repo.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Invalid employee Id:" + id));
    }

    public Employee updateEmployee(Employee employee) {
        return repo.save(employee);
    }

    @GetMapping("/employee/edit/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        model.addAttribute("employees2", getEmployeeId(id));
        return "update";
    }

    @PostMapping("/employee/{id}")
    public String update(@PathVariable int id,
                         @ModelAttribute("employees2") Employee employee) {
        Employee emp = getEmployeeId(id);
        emp.setName(employee.getName());
        emp.setDesignation(employee.getDesignation());
        emp.setSalary(employee.getSalary());
        updateEmployee(emp);
        return "redirect:/index";
    }

    @GetMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable int id) {
        repo.deleteById(id);
        return "redirect:/index";
    }
}

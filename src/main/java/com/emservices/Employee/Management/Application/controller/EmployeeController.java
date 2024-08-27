package com.emservices.Employee.Management.Application.controller;

import com.emservices.Employee.Management.Application.dto.EmployeeRequest;
import com.emservices.Employee.Management.Application.dto.EmployeeResponse;
import com.emservices.Employee.Management.Application.entity.Employee;
import com.emservices.Employee.Management.Application.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("http://localhost:5173/")
//@CrossOrigin("https://employee-ms-vinay.netlify.app/")
@CrossOrigin("https://technocratshub.online/Vinay_Thaware/EmployeeMS/")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

//    for getting all employees
@GetMapping("employees")
public List<EmployeeResponse> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int limit,
                                              @RequestParam(required = false) String search) {
    List<EmployeeResponse> employeeList = employeeService.getAllEmployees(page, limit, search);
    return employeeList;
}


    //    for getting all employee by id
    @GetMapping("employee/{id}")
    public Employee getEmployeeById(@PathVariable("id") Integer id){
        Employee employee=employeeService.getEmployeeById(id);
        return employee;
    }


    //   for added employees
    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody EmployeeRequest employeeRequest){
        Employee employee =employeeService.addEmployeeDetails(employeeRequest);
        return employee;
    }

    //   for added employees
    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable("id") Integer id,@RequestBody EmployeeRequest employeeRequest){
        Employee employee =employeeService.updateEmployeeDetails(employeeRequest,id);
        return employee;
    }

    //for delete employee by id
    @DeleteMapping("employee/{id}")
    public String deleteEmployeeById(@PathVariable("id") Integer id){
      employeeService.deleteEmployeeById(id);
        return "Delete successfully";
    }
}

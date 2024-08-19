package com.emservices.Employee.Management.Application.service;

import com.emservices.Employee.Management.Application.dto.EmployeeRequest;
import com.emservices.Employee.Management.Application.dto.EmployeeResponse;
import com.emservices.Employee.Management.Application.entity.Employee;
import com.emservices.Employee.Management.Application.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
@Autowired
    EmployeeRepository employeeRepository;

    public Employee addEmployeeDetails(EmployeeRequest employeeRequest) {

         Employee employee=new Employee();
         employee.setName(employeeRequest.getName());
         employee.setEmpId(generateEmployeeId());
         employee.setMobile(employeeRequest.getMobile());
         employee.setEmail(employeeRequest.getEmail());
         employee.setDateOfJoining(employeeRequest.getDateOfJoining());
         employeeRepository.save(employee);
         return employee;
    }

    private String generateEmployeeId() {
        // Get the list of last employee IDs from the database
        List<String> lastEmpIds = employeeRepository.findLastEmployeeIds();

        // If there are any IDs, take the first one as the latest
        if (!lastEmpIds.isEmpty()) {
            String lastEmpId = lastEmpIds.get(0);
            String lastIdNumberStr = lastEmpId.substring(lastEmpId.length() - 3);
            int newIdNumber = Integer.parseInt(lastIdNumberStr) + 1;
            return "Emp" + String.format("%03d", newIdNumber);
        }
        // If no employees exist, start with Emp101
        return "Emp101";
    }

    public Employee getEmployeeById(Integer id) {
        Optional<Employee> employee= employeeRepository.findById(id);

        Employee employee1=new Employee();
        employee1.setId(employee.get().getId());
        employee1.setName(employee.get().getName());
        employee1.setEmpId(employee.get().getEmpId());
        employee1.setEmail(employee.get().getEmail());
        employee1.setMobile(employee.get().getMobile());
        employee1.setDateOfJoining(employee.get().getDateOfJoining());

        return employee1;

    }


    public List<EmployeeResponse> getAllEmployees(int page, int limit, String search) {
        List<Employee> employeeList;
        long totalCount;

        if (search != null && !search.isEmpty()) {
            List<Employee> allMatchingEmployees = employeeRepository.findByNameContainingIgnoreCase(search);

            int start = Math.min(page * limit, allMatchingEmployees.size());
            int end = Math.min((page + 1) * limit, allMatchingEmployees.size());
            employeeList = allMatchingEmployees.subList(start, end);

            // Calculate total count based on search results
            totalCount = allMatchingEmployees.size();
        } else {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Employee> employeePage = employeeRepository.findAll(pageable);
            employeeList = employeePage.getContent();

            // Store total count directly in the variable
            totalCount = employeePage.getTotalElements();
        }

        // Convert Employee to EmployeeResponse and set totalCount
        List<EmployeeResponse> employeeResponses = employeeList.stream()
                .map(employee -> {
                    EmployeeResponse response = new EmployeeResponse();
                    // Map fields from Employee to EmployeeResponse
                    response.setId(employee.getId());
                    response.setName(employee.getName());
                    response.setEmpId(employee.getEmpId());
                    response.setMobile(employee.getMobile());
                    response.setEmail(employee.getEmail());
                    response.setDateOfJoining(employee.getDateOfJoining());
                    response.setTotalCount(totalCount);
                    return response;
                })
                .collect(Collectors.toList());

        // You can now use totalCount directly where needed
        // For example, you could return the employeeResponses and handle totalCount elsewhere if necessary
        return employeeResponses;
    }

    public void deleteEmployeeById(Integer id) {
        Optional<Employee> employeeOptional=employeeRepository.findById(id);
        if(employeeOptional.isPresent()){
            System.out.println("User id is"+employeeOptional.get().getId());
        }else{
            System.out.println("User Id Not Found");
        }
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployeeDetails(EmployeeRequest employeeRequest,Integer id) {
        Optional<Employee> employeeOptional=employeeRepository.findById(id);
       Employee employee= employeeOptional.get();
        employee.setName(employeeRequest.getName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setEmpId(employee.getEmpId());
        employee.setMobile(employeeRequest.getMobile());
        employee.setDateOfJoining(employeeRequest.getDateOfJoining());
        employeeRepository.save(employee);

        return employee;
    }
}

package com.emservices.Employee.Management.Application.repository;

import com.emservices.Employee.Management.Application.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query("SELECT e.empId FROM Employee e ORDER BY e.empId DESC")
    List<String> findLastEmployeeIds();


    Employee findById(Long id);

    List<Employee> findByNameContainingIgnoreCase(String name);
}

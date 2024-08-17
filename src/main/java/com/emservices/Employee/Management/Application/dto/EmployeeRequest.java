package com.emservices.Employee.Management.Application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeRequest {
    private Long id;

    private String name;

    private String empId;

    private Long mobile;

    private String email;

    private LocalDate dateOfJoining;
}

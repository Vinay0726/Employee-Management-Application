package com.emservices.Employee.Management.Application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String name;
    private String empId;
    private Long mobile;
    private String email;
    private LocalDate dateOfJoining;

    // This field will be used to send the total count
    private Long totalCount;

    public EmployeeResponse(Long totalCount) {
        this.totalCount = totalCount;
    }

}

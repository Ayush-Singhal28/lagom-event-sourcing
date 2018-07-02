package com.example.ayush.example.impl;

import com.example.ayush.example.api.Employee;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Builder
public class EmployeeState {
    
    Optional<Employee> employee;
    
    EmployeeState(Optional<Employee> employee) {
        this.employee = employee;
    }
}

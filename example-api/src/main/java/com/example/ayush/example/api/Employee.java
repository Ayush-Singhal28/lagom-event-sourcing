package com.example.ayush.example.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Builder
@Value
@JsonDeserialize
@AllArgsConstructor
public class Employee {
    
    String empId;
    
    String firstName;
    
    String lastName;
    
    int age;
}

package com.example.ayush.example.impl;

import akka.Done;
import com.example.ayush.example.api.Employee;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

public interface EmployeeCommand extends Jsonable {
    
    @Builder
    @Value
    
    final class CreateEmployeeDetails implements EmployeeCommand, PersistentEntity.ReplyType<Done> {
        Employee employee;
    }
    
    @Builder
    @Getter
    final class GetEmployeeDetails implements  EmployeeCommand, PersistentEntity.ReplyType<String> {
        String welcome;
        
        GetEmployeeDetails(String welcome) {
            this.welcome = welcome;
        }
    
    }
}

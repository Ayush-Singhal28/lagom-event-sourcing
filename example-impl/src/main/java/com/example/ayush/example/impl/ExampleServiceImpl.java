package com.example.ayush.example.impl;

import akka.Done;
import akka.NotUsed;
import akka.japi.Pair;
import com.example.ayush.example.api.Employee;
import com.example.ayush.example.api.ExampleService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.broker.TopicProducer;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;


public class ExampleServiceImpl implements ExampleService {
    
    private final PersistentEntityRegistry persistentEntityRegistry;
    
    @Inject
    public ExampleServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        persistentEntityRegistry.register(EmployeeEntity.class);
    }
    
    @Override
    public ServiceCall<NotUsed, String> hello() {
        return request -> CompletableFuture.completedFuture("hello");
        
    }
    
    @Override
    public ServiceCall<Employee, Done> enterDetails() {
        return request -> {
            PersistentEntityRef<EmployeeCommand> ref = persistentEntityRegistry.refFor(EmployeeEntity.class,
                    request.getEmpId());
            
            return ref.ask(EmployeeCommand
                    .CreateEmployeeDetails.builder()
                    .employee(request).build());
        };
    }
    
    @Override
    public ServiceCall<NotUsed, String> getDetails() {
        return request -> {
            PersistentEntityRef<EmployeeCommand> ref = persistentEntityRegistry.refFor(EmployeeEntity.class, "1");
            return ref.ask(EmployeeCommand.GetEmployeeDetails.builder().welcome("Hello").build());
        };
    }
    
    @Override
    public Topic<Employee> publishInfo() {
        return TopicProducer.singleStreamWithOffset(offset ->
                persistentEntityRegistry.eventStream(EmployeeEvents.TAG, offset)
                        
                        .map(pair -> {
                            Employee employee;
                            if (!(pair.first() instanceof EmployeeEvents.EmployeeDetailsCreated)) {
                                throw new IllegalArgumentException("Unknown event: " + pair.first());
                            }
    
    
                            EmployeeEvents.EmployeeDetailsCreated message = (EmployeeEvents.EmployeeDetailsCreated) pair.first();
                            employee = message.getEmployee();
                            
                            return Pair.create(employee, pair.second());
                        })
        );
    }
}

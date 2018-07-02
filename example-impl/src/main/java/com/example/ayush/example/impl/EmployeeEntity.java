package com.example.ayush.example.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class EmployeeEntity extends PersistentEntity<EmployeeCommand,EmployeeEvents, EmployeeState> {
    
    @Override
    public Behavior initialBehavior(Optional<EmployeeState> snapshotState) {
        BehaviorBuilder behaviorBuilder = newBehaviorBuilder(EmployeeState
                .builder()
                .employee(Optional.empty())
        .build());
        
        behaviorBuilder.setCommandHandler(EmployeeCommand.CreateEmployeeDetails.class,(cmd,ctx)
        ->  ctx.thenPersist(EmployeeEvents
                .EmployeeDetailsCreated
                .builder().employee(cmd.getEmployee()).entityId(entityId()).build(), evt ->
        ctx.reply(Done.getInstance())));
        
        behaviorBuilder.setEventHandler(EmployeeEvents.EmployeeDetailsCreated.class,evt ->
        EmployeeState.builder().employee(Optional.of(evt.getEmployee())).build());
        
        behaviorBuilder.setReadOnlyCommandHandler(EmployeeCommand.GetEmployeeDetails.class, (cmd,ctx) ->
        ctx.reply(state().employee+""+cmd.getWelcome()+"------------"));
        
       return behaviorBuilder.build();
        
    }
}

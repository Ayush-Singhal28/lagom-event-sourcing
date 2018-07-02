package com.example.ayush.example.impl;

import com.example.ayush.example.api.Employee;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Builder;
import lombok.Getter;

public interface EmployeeEvents extends Jsonable,  AggregateEvent<EmployeeEvents> {
    
    static AggregateEventTag<EmployeeEvents> TAG = AggregateEventTag.of(EmployeeEvents.class);
    
    
    @Builder
    @Getter
     class EmployeeDetailsCreated implements EmployeeEvents {
        
        private final Employee employee;
        private final String entityId;
        
        EmployeeDetailsCreated(Employee employee, String entityId) {
            this.employee = employee;
            this.entityId = entityId;
        }
    
    
        @Override
        public AggregateEventTagger<EmployeeEvents> aggregateTag() {
            return TAG;
        }
    }
    
    
}

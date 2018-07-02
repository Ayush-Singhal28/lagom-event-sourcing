package com.example.ayush.example.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.topic;



public interface ExampleService extends Service {

    ServiceCall<NotUsed, String> hello();
    ServiceCall<Employee, Done> enterDetails();
    ServiceCall<NotUsed,String> getDetails();
    Topic<Employee> publishInfo();
    String EMP_TOPIC = "employee-topic";

    @Override
    default Descriptor descriptor() {
        return named("hello").withCalls(
                Service.restCall(Method.GET,"/api/id", this::hello),
                Service.restCall(Method.POST,"/api/enterDetails",this::enterDetails),
                Service.restCall(Method.GET,"/api/getDetails",this::getDetails)
        )
                .withTopics(
                        Service.topic(EMP_TOPIC,this::publishInfo)
                )
                .withAutoAcl(true);

    }
}

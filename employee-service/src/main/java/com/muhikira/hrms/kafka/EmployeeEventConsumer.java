//package com.muhikira.hrms.kafka;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmployeeEventConsumer {
//
//    @KafkaListener(topics = "employee-topic", groupId = "hrms-group")
//    public void consumeEmployeeEvent(String message) {
//        System.out.println("Consumed Employee Event: " + message);
//        // Logic to handle the employee event
//    }
//}

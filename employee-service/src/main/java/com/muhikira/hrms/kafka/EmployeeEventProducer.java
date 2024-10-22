//package com.muhikira.hrms.kafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmployeeEventProducer {
//
//    private static final String TOPIC = "employee-topic";
//
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    public void sendEmployeeEvent(Object event) {
//        kafkaTemplate.send(TOPIC, event);
//    }
//}

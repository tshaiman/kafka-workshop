package com.kworkshop.lab03;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class KProducer {

    public static void main(String[] args){
        System.out.println("KProducer Main App");
        Produce("127.0.0.1:9092");
    }

    public static void Produce(String brokers) {

        //task 1.1 create a Kafka Producer
        KafkaProducer<String,String> producer = null;

        // task 1.2 Send 1000 message with Key as the Current time in miliseonds (String) and value - a random string.

        //task 1.3 add a callback an print a confirmation message after the message was sent

    }

    public static KafkaProducer<String,String> BuildProducer(String brokers) {
        Properties properties = new Properties();
        //1. Set Bootstrap Servers
        //2. Set Key /Value Serializers
        //3. Set ACKS to "1"
        return new KafkaProducer<String,String>(properties);
    }
}

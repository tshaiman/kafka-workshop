package com.kworkshop.lab03;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class KProducer {

    public static void main(String[] args){
        System.out.println("KProducer Main App");
        Produce("127.0.0.1:9092");
    }



    public static void Produce(String brokers) {

        //task 1.1 create a Kafka Producer
        KafkaProducer<String,String> producer = BuildProducer(brokers);

        // task 1.2 Send 1000 message with Key as the Current time in miliseonds (String) and value - a random string.
        //task 1.3 add a callback an print a confirmation message after the message was sent
        while(true){
            producer.send(new ProducerRecord<String, String>("my-topic", Long.toString(System.currentTimeMillis()),
                    UUID.randomUUID().toString()), (recordMetadata, e) -> {
                            System.out.println("completed sending . partition = " + recordMetadata.partition() + " offset " + recordMetadata.offset());
                    });
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static KafkaProducer<String,String> BuildProducer(String brokers) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        properties.setProperty(ProducerConfig.ACKS_CONFIG,"1");
        return new KafkaProducer<String,String>(properties);
    }
}

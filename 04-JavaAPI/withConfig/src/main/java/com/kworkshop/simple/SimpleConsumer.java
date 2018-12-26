package com.kworkshop.simple;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class SimpleConsumer {

    public static void main(String[] args) {
        System.out.println("Simple Consumer with Config");
        Consume();
    }
    private static KafkaConsumer<String,String> consumer ;

    protected static void Consume() {

        String topic = ConfigManager.getTopicName();
        consumer = buildConsumer();
        consumer.subscribe(Arrays.asList(topic));

        int count = 0;
        System.out.println("Start consuming from topic " +topic);
        while(true) {

            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(500));
            for(ConsumerRecord<String,String> record : records) {
                // Display record and count
                count += 1;
                System.out.println( count + ": " + record.value());
            }
        }
    }

    public static KafkaConsumer<String,String> buildConsumer()
    {
        Properties properties = ConfigManager.getConsumerConfig();
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);
        return consumer;
    }


}

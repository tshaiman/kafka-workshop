package com.kworkshop.simple;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

public class SimpleConsumerAssignAndSeek {


    private static KafkaConsumer<String,String> consumer ;
    private static String topic = "my-topic";

    public static void main(String[] args) {

        String brokers = "localhost:9092";
        String groupId;
        if(args.length > 0) {
            groupId = args[0];
        } else {
            groupId = UUID.randomUUID().toString();
        }

        Consume(brokers, groupId);
    }

    protected static void Consume(String brokers, String groupId) {
        consumer = BuildConsumer(brokers,groupId);
        TopicPartition tp = new TopicPartition(topic,0);
        long offset = 15L;
        int max_read = 5;
        int read = 0;
        boolean keepReading = true;

        consumer.assign(Arrays.asList(tp));
        consumer.seek(tp,0);

        while(keepReading) {

            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord<String,String> record : records) {
                // Display record and count
                System.out.println( record.value());
                read++;
                if(read >= max_read) {
                    keepReading = false;
                    break;
                }

            }

        }

    }

    public static KafkaConsumer<String,String> BuildConsumer(String brokers, String groupId)
    {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers",brokers);
        properties.setProperty("bootstrap.servers",brokers);
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());

        properties.setProperty("enable.auto.commit","true");
        properties.setProperty("group.id",groupId);
        // When a group is first created, it has no offset stored to start reading from. This tells it to start
        // with the earliest record in the stream.
        properties.setProperty("auto.offset.reset","earliest");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        return consumer;
    }


}

package com.kworkshop.lab03;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

public class KConsumer {

    public static void main(String[] args){
        //task 1.6 : run 1 consuer and 1 producer to verify the results
        //make sure the producer produces at a rate of 100 messages per second.
        System.out.println("KConsumer Main App");


    }

    protected static void Consume(String brokers, String groupId) {

        //////////////////////
        //task 1.4 create a consumer with the correct properties. use the method CreateConsumer below
        KafkaConsumer<String,String> consumer = null;
        //////////////////////

        //////////////////////
        //task 1.5 register for the topic my-topic
        //TODO
        ////////////////////


        while(true) {
            //task 1.5 : poll messages from the topic and print the following information:
            //parition : 1 , Offset : 0 , Key : my-key , Value : my-value

            //sleep 1 milisecond between messages
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    public static KafkaConsumer<String,String> BuildConsumer(String brokers,String groupId) {
        Properties properties = new Properties();
        //1.Add bootstrap.servers config
        //2.Add Key and Value Serializers
        //3.Enable auto commit to true
        //4.set Auto Reset Offset to 'earliest'
        //5.Set the group Id
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        return consumer;
    }


}

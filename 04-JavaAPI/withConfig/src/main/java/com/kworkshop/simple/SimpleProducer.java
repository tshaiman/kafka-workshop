package com.kworkshop.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class SimpleProducer {

    public static void main(String[] args) throws IOException {
        System.out.println("Simple Producer with Config");
        Produce();

    }
    public static void Produce() throws IOException {

        KafkaProducer<String,String> producer  = buildProducer();
        String topic = ConfigManager.getTopicName();

        // So we can generate random sentences
        Random random = new Random();
        String[] sentences = new String[] {
                "the cow jumped over the moon",
                "an apple a day keeps the doctor away",
                "four score and seven years ago",
                "snow white and the seven dwarfs",
                "i am at two with nature"
        };

        String progressAnimation = "|/-\\";
        // Produce a bunch of records
        for(int i = 0; i < 1000; i++) {
            // Pick a sentence at random
            String sentence = sentences[random.nextInt(sentences.length)];
            // Send the sentence to the test topic
            producer.send(new ProducerRecord<>(topic, sentence));
            String progressBar = "\r" + progressAnimation.charAt(i % progressAnimation.length()) + " " + i;
            System.out.write(progressBar.getBytes());
        }

        System.in.read();

    }



    public static KafkaProducer<String,String> buildProducer()
    {
        Properties properties = ConfigManager.getProducerConfig();
        return new KafkaProducer<>(properties);

    }
}




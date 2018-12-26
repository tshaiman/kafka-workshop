package twitterApp.producer;

import com.twitter.hbc.core.Client;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.*;

import static java.lang.Runtime.getRuntime;


public class TwitterProducer {

    Client twitterClient;
    Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());

    KafkaProducer<String,String> producer;
    public static final String topic = "tweets";

    public static void main(String[] args) {
        new TwitterProducer().run();
    }

    private void run()  {

        //////////Part 1 - Basic Configs ///////////////////
        //Build Tweeter Client
        //Create a Kafka Producer
        //poll the Queue
        //////////Part 2 - Idempotent Producer ///////////////////

    }


    public static KafkaProducer<String,String> buildProducer(String brokers)
    {
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(properties);

    }


}

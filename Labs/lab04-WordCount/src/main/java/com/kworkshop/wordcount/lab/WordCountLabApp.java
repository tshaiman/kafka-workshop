package com.kworkshop.wordcount.lab;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class WordCountLabApp {

    //TODO Task 1. Define your own input and output Names
    static String inTopic = "";
    static String outTopic = "";
    static String appId = "streams-wordcount";

    public static void main(String[] args) {

        StreamsBuilder builder = new StreamsBuilder();
        //Create A stream
        KStream<String, String> textLines = builder.stream(inTopic);

        //TODO : Task 2
        //   Build a KTable That gets the stream and
            // Change all words to Capital Letters
            // Flat map the Sentance to a List of Words
            // Filters out words that less than 3 letters
            // group by the value
            // count

        KTable<String, Long> wordCounts = null; // TODO : CHANGE THIS

        //TODO : Task 3
        //TASK 3 : Write the results back to Kafka using .toStream.to(TopicNAme,Produced.With())


        Properties props = getProperties();
        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-wordcount-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });


        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);


        //TODO Task 4
        //1. Create two topics according to the name you have chosed
        //2. follow the scripts.txt in order to produce and consume records
    }


    private static Properties getProperties(){
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, appId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // setting offset reset to earliest so that we can re-run the demo code with the same pre-loaded data
        // Note: To re-run the demo, you need to use the offset reset tool:
        // https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Streams+Application+Reset+Tool
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }
}

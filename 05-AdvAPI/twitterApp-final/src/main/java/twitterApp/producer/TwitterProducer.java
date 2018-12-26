package twitterApp.producer;

import com.twitter.hbc.core.Client;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class TwitterProducer {

    Client twitterClient;
    Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());

    public static final String topic = "tweets";
    private AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        new TwitterProducer().run();
    }

    private void run()  {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        twitterClient = TwitterClient.createTwitterClient(queue,"bitcoin","sport","football","usa","facebook");
        KafkaProducer<String,String> producer = BuildProducer("localhost:9092");
        addShutDownHook();

        logger.info("Start polling tweets");


        while(!twitterClient.isDone() && counter.get() < 1000){
            String tweet = null;
            try {
                tweet = queue.poll(200, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.error("could not poll from twitter",e);
            }
            if(tweet != null){
                final String theTweet = tweet;
                //send to kafka
                producer.send(new ProducerRecord<>(topic, theTweet), (recordMetadata, e) -> {
                    //logger.info("tweet sent to kafka {}. partition : {}",theTweet,recordMetadata.partition());
                    counter.incrementAndGet();
                });
            }
        }
    }

    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
           logger.info("Stopping...");
           twitterClient.stop();
           logger.info("Stpped");
        }));
    }


    public static KafkaProducer<String,String> BuildProducer(String brokers)
    {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Idempotent producer
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG,"all");
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,"5");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG,String.valueOf(Integer.MAX_VALUE));

        //batch and high performance
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,String.valueOf(32 * 1024));
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG,"20");

       return new KafkaProducer<>(properties);

    }




}

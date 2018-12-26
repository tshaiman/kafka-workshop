package twitterApp.consumer;

import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class TwitterConsumer {

    static Logger logger ;
    static RestHighLevelClient client ;

    public static void main(String[] args) throws IOException, InterruptedException {
        logger = LoggerFactory.getLogger("ElasticWriter");
        client = createClient();
        KafkaConsumer<String,String> consumer = buildConsumer();

        while(true) {

            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));
            int recordCount = records.count();

            if(recordCount > 0 ) {
                logger.info("processing " + recordCount + " records");
                processBatchTweets(records);
                logger.info("Committing offsets....");
                consumer.commitSync();
                logger.info("Offsets has been committed");
            }else {
                logger.info("no-data");
            }

        }

        //client.close();
    }

    private static void processBatchTweets(ConsumerRecords<String,String> records) throws IOException, InterruptedException {
        BulkRequest bulkRequest = new BulkRequest();
        for(ConsumerRecord<String,String> record : records) {
            //1. Creating Kafka Generic Id
            //String id = record.topic() + record.partition() + record.offset();

            //2. Twitter id :
            try {
                String id = extractIdFromTweet(record.value());

                // Display record and count
                IndexRequest indexReq = new IndexRequest(
                        "twitter",
                        "tweets",
                        id)
                        .source(record.value(), XContentType.JSON);

                bulkRequest.add(indexReq);
            } catch (NullPointerException ex) {
                logger.warn("skipping bad data" + record.value());
            }
        }

        //IndexResponse indexResponse = client.index(indexReq, RequestOptions.DEFAULT);
        logger.info("inset bulk to elastic");
        BulkResponse bulkResponse = client.bulk(bulkRequest,RequestOptions.DEFAULT);
        Thread.sleep(10);

    }

    private static JsonParser jsonParser = new JsonParser();
    private static String extractIdFromTweet(String tweetJson) {
       return jsonParser.parse(tweetJson)
                .getAsJsonObject()
                .get("id_str")
                .getAsString();
    }

    public static KafkaConsumer<String,String> buildConsumer()
    {
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "kafka-elasticsearch";
        String topicName = "tweets";

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"50");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName));
        return consumer;
    }

    public static RestHighLevelClient createClient(){
        String hostname = "host";
        String username = "";
        String password = "";


        final CredentialsProvider credProvider = new BasicCredentialsProvider();
        credProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(username,password));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(hostname,443,"https"))
                .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setDefaultCredentialsProvider(credProvider));
        return new RestHighLevelClient(builder);
    }
}

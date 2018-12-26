import kafka.admin.RackAwareMode;
import kafka.zk.AdminZkClient;
import kafka.zk.KafkaZkClient;
import org.apache.kafka.common.utils.Time;
import scala.collection.Map;


import java.util.Properties;

public class CreateTopic {

    static String zookeeperHost = "127.0.0.1:2181";
    static Boolean isSucre = false;
    static int sessionTimeoutMs = 200000;
    static int connectionTimeoutMs = 15000;
    static int maxInFlightRequests = 10;
    static Time time = Time.SYSTEM;
    static String metricGroup = "myGroup";
    static String metricType = "myType";


    public static void main(String[] args) {
        //createTopic();
        listTopics();
    }

    public static void createTopic(){
        KafkaZkClient zkClient = KafkaZkClient.apply(zookeeperHost, isSucre, sessionTimeoutMs,
                connectionTimeoutMs, maxInFlightRequests, time, metricGroup, metricType);

        AdminZkClient adminZkClient = new AdminZkClient(zkClient);

        String topicName1 = "my-topic-2";
        int partitions = 3;
        int replication = 1;
        Properties topicConfig = new Properties();

        adminZkClient.createTopic(topicName1, partitions, replication,topicConfig, RackAwareMode.Disabled$.MODULE$);
        System.out.println("Topic " + topicName1 + " was created successfully");

    }

    private static void listTopics() {
        KafkaZkClient zkClient = KafkaZkClient.apply(zookeeperHost, isSucre, sessionTimeoutMs,
                connectionTimeoutMs, maxInFlightRequests, time, metricGroup, metricType);

        AdminZkClient adminZkClient = new AdminZkClient(zkClient);
        Map<String,Properties> topics = adminZkClient.getAllTopicConfigs();
        scala.collection.Iterator iter = topics.keysIterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

    }

}

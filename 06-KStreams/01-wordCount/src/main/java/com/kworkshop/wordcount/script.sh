

///// 1.Create The Topics ////////////

bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic wc-input --replication-factor 1 --partitions 2
bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic wc-output --replication-factor 1 --partitions 2

///////////2. Run the WordCount-App//////////


////////3. Run the Consumer listener :///////////////

bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 \
--topic wc-output  \
--property print.key=true \
--property print.value=true \
--property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
--property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer


//////////4. Run the Producer to produce some sentences://////////////

bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic wc-input

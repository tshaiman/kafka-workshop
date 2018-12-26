# Kafka Workshop - Lab 02: Kafka CLI Commands 
---
<img src="https://kafka.apache.org/images/apache-kafka.png" height="120" />

## Purpose 

In this lab you will create topics and lists topics , publish data and consume it via the CLI tool.



## Task 1 : create a Kafka Topic name "kafka-ws"

 1. Run the Kafka Command that creates a topic with 3 partitions and a replication factor of 2:

 ```
 $ kafka-topic.sh --zookeeper 127.0.0.1:2181 --topic kafka-ws --partitions 3 --replication-factor 2
 ```

 2. The Previous command has failed. read the error message and try to fix the command in order to make it work.
 (Hint : you are on a Dev Environment with only 1 Node at the cluster)

 3. List all the topics in the cluster and make sure your new topic is displayed.
 ```
 $ kafka-topic.sh --zookeeper 127.0.0.1:2181 --topic --list
 ```

## Task 2 : Produce and consume some Items using the CLI Tool


 1.	Create a simple Producer prompt that will allow you to send some string data to the topic :

 ```
 $ kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic myTopic 
 ```
 
 2.	Open a second Terminal and consume the records :

 ```
 $ kafka-console-consumer.sh --broker-list 127.0.0.1:9092 --topic myTopic --from-beginning
 ```

3. close the consumer process and re-launch it. What happens when you run the command without the --from-beginning flag ?

4. Use diffrent consumer groups in order to understand how consumer groups handles the read offset for each group seperatley.

 ```
 $ kafka-console-consumer.sh --broker-list 127.0.0.1:9092 --topic myTopic --from-beginning --consumer-property group.id=group1
 ```

## Good Luck !

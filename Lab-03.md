# Kafka Workshop - Lab 03: Java API and Rebalance 
---
<img src="https://kafka.apache.org/images/apache-kafka.png" height="120" />

## Purpose 

In this lab you will interact with the Java API and use multiple producers on the same consumer group in order to view how Kafka Re-balances
the topic partitions among several consumers that belong to the same group.

First you will create a single producer and a single consumr , then you will run multiple consumers processes.

 
## Task 1 : complete the Kafka Producer and Consumer Code

 1. First create the topic that will be used for this lab
  ```
  $ kafka-topic.sh --zookeeper 127.0.0.1:2181 --topic my-topic --partitions 3 --replication-factor 1
  ``` 
  
 1. Open Lab03 starter code by from the course matterials : /KafkaWorkshop/Labs

 2. Under lab03 folder you will find the source code started for the KProducer.java. 
 follow the instructions on the code to complete the producer code

 3. Under lab03 folder you will find the source code started for the KConsumer.java. 
 follow the instructions on the code to complete the consumer code

 4. From IntelliJ run the two main methods for both the consumer and the producer and verify the consumer outputs the data.
 
 (Note the partition numbers on the output)

## Task 2 (Optional)

 1. Run several Consumers on the same topic with different Group Id and  
    make sure you see the re-balance messages.
 2. try to figure out How many partitions each producer has ?   

## Good Luck !

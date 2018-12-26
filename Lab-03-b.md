# Kafka Workshop - Lab 03: Java API and Rebalance 
---
<img src="https://kafka.apache.org/images/apache-kafka.png" height="120" />

## Purpose 

In this lab you will interact with the Java API and use multiple producers on the same consumer group in order to view how Kafka Re-balances
the topic partitions among several consumers that belong to the same group.

First you will create a single producer and a single consumr , then you will run multiple consumers processes.

 
## Task 1 : Inspect the Code of the Producer and Consumer

 1. First create the topic that will be used for this lab
  ```
  $ kafka-topic.sh --zookeeper 127.0.0.1:2181 --topic my-topic --partitions 3 --replication-factor 1
  ``` 
  
 1. Open 04-JavaAPI folder and look for 'Simple' folder

 2. Inspect the code in the producer and make sure you understand it. run the program

 3. Inspect the code in the consumer and make sure you understand it. run the program
 
 4. run the consumer with the same consumer groups and then with diffrent consumer groups.
 
 (Note the partition numbers on the output)

## Task 2 

 1. Run several Consumers on the same topic with different Group Id and  
    make sure you see the re-balance messages.
 2. try to figure out How many partitions each producer has ?   

## Good Luck !

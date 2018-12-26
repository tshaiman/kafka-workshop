# Kafka Workshop - Lab 04: Kafka Streams
---
<img src="https://kafka.apache.org/images/apache-kafka.png" height="120" />

## Purpose 

In this lab you will run a simple kafka Stream (Word Count) and you will add additional filter to it.
The goal is to get you familiar with the basic Stream concepts and how it is ran with the usage of the CLI tools

You will start with a pre-made project and add the missing parts to it during the lab.



## Task 1 : Complete the Word Count Lab code 

 1. Open Lab04-WordCount starter code by from the course matterials : /KafkaWorkshop/Labs/Lab05-WordCount

 2. look for //TODO Task 1 and choose an input topic name and an output topic name for your toplogy
 
 3. look for //TODO : Task 2 and complete the code that builds the KTable object.
 * you will need to create a KTable object from the textLines, 
 * change each line to capital letter by using the **map** function
 * flat the sentence to a stream of words by calling **flatMapValues(textLine -> Arrays.asList(textLine.split("\\W+")))**
 * filter all the words that their length is smaller than 5
 * filter all the words that are 
 * group by value and conut

 use the 05-Kstream project as a reference for this task

  4. look for //TODO : Task 3 in order to publish the stream back to kafka, for example :
  
 ```
  wordCounts.toStream().to(outTopic, Produced.with(Serdes.String(),Serdes.Long()));
 ```
 
 (Note the partition numbers on the output)


## Task 2 : Create Topics and run the topology


 1.	Open the file /KafkaWorkshop/Labs/Lab05-WordCount/scripts.txt and use it to create 2 topics 
 make sure to use the same names you have used in Task 1

 ```
bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic wc-input --replication-factor 1 --partitions 2
bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic wc-output --replication-factor 1 --partitions 2
 ```

 2. Run your app in order to run the toplogy

 3. Run the console consumer in order to view the Output topic After the Stream transformation:

```
 bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 \
--topic wc-output  \
--property print.key=true \
--property print.value=true \
--property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
--property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```

4. Run the console Producer in order to produce some sentences to the input topic

```
bin/kafka-console-producer.sh --bootstrap-server 127.0.0.1:9092 --topic wc-output

```



## Good Luck !

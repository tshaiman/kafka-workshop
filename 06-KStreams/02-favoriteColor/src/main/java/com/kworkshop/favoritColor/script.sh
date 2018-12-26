#!/bin/bash

# create input topic with one partition to get full ordering
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic fav-color-in

# create intermediary log compacted topic
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic user-colors

# create output log compacted topic
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic fav-color-out

# launch a Kafka consumer
kafka-console-consumer --bootstrap-server localhost:9092 \
    --topic fav-color-out \
    --from-beginning \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

# launch the streams application

# then produce data to it
kafka-console-producer --broker-list localhost:9092 --topic fav-color-in
#
stephane,blue
john,green
stephane,red
alice,red


# list all topics that we have in Kafka (so we can observe the internal topics)
kafka-topics --list --zookeeper localhost:2181

#!/bin/bash

#Create a topic
# create input topic with two partitions
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 2 --topic my-topic

#producer some data
kafka-console-producer.sh --broker-list localhost:9092 --topic my-topic

#start connect standalone
connect-standalone.sh connect-standalone.properties local-file-sink.properties
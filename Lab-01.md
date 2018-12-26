# Kafka Workshop - Lab 01: Install and Run Kafka
---
<img src="https://kafka.apache.org/images/apache-kafka.png" height="120" />

## Purpose 

In this lab you will interact with Kafka Cluster and its ecosystem using the landoop docker image for developers.
Please remember that this docker image is only for Dev and Testing purposes and that a real Kafka Cluster must hold several Brokers.



## Task 1 : Install and Run Kafka on your local machine

1. Make sure you have a running docker installed on your machine by running :
```
$ docker -v 
```


2. Run the following command to run the Kafka Image:
```
docker run --rm -it \
           -p 2181:2181 -p 3030:3030 -p 8081:8081 \
           -p 8082:8082 -p 8083:8083 -p 9092:9092 \
           -e ADV_HOST=127.0.0.1 \
           landoop/fast-data-dev

```

 3. After the installation is done, browse to http://localhost:3030 and make sure you can view the Portal

## Task 2 : Interact with the Kafka UI Portal of the Landoop environment

By interacting with the portal intuitively , try to answer the following questions :

 1.	How many partitions does the reddit_post topic has?
 
 2.	What is the Longitude value for the item in offset-1 in partition-0 for the topic sea_vessel_position_reports ? 

 3.	How many Brokers does this development environment has ?

 4.	What is the default port for Zookeeper ? what is the port for Schema Registry ?

 5.	How many fields are defined in the schema for the reddit_post topic ?


## Good Luck !

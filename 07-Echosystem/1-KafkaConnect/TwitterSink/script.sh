
#!/bin/bash

#goto Confluent Connect page :
#https://www.confluent.io/hub/

#goto twitter source-git hub and download the latest release
#https://github.com/jcustenborder/kafka-connect-twitter/releases

#extract the twitter connector into connectors folder

#create topic
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic twitter_status_connect
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic twitter_deletes_connect

#start a consumer
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic twitter_status_connect --from-beginning

#run the standalone connect
connect-standalone.sh connect-standalone.properties twitter.properties
# Spring Apache Kafka

Apache Kafka® is a distributed streaming platform

### Installation

Apache Kafka requires [Java](https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html) v8 to run.

Install the jdk and set the JAVA_HOME and path.

Install the [Apache Kafka](https://kafka.apache.org/downloads) and extract the folder from the zip

Edit the system environment PATH variable and append the following path 
```sh
E:\kafka-12\bin\windows
```
`Note: The above path should match the directory  where the kafka folder is extracted`

- To start the zookeeper server 
```sh
E:\kafka_2.12-2.5.0 > zookeeper-server-start config/zookeeper.properties
```
- To start the kafka server or broker
```sh
E:\kafka_2.12-2.5.0 >kafka-server-start config/server.properties
```
# Apache Kafka CLI
--- 
#### Topic
- To create a topic
```sh
E:\kafka_2.12-2.5.0>kafka-topics --zookeeper 127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1
```
- To view list of available topics
```sh
E:\kafka_2.12-2.5.0>kafka-topics --zookeeper 127.0.0.1:2181 --list
```
- To describe a topic 
```sh
E:\kafka_2.12-2.5.0>kafka-topics --zookeeper 127.0.0.1:2181 --topic first_topic --describe
```
- To delete a topic 
```sh
E:\kafka_2.12-2.5.0>kafka-topics --zookeeper 127.0.0.1:2181 --topic first_topic --delete
```

#### Producer
- To produce data to an existing topic 
```sh
E:\kafka_2.12-2.5.0>kafka-console-producer --broker-list 127.0.0.1:9092 --topic
first_topic --producer-property acks=all
```
- To producte data to a non existing topic
```sh
Produce to a topic that does not exists
E:\kafka_2.12-2.5.0>kafka-console-producer --broker-list 127.0.0.1:9092 --topic new_topic
```
`Note : This command will give throw us a warning since the topic does not exists.It will however create a topic by name new_topic with default server.properties`

#### Consumer
- To read a message 
```sh
E:\kafka_2.12-2.5.0>kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning
```
`Note: --from-beginning is optional. It will allow the consumer to read messages from the beginning`

- To read a message from consumer group
```sh
E:\kafka_2.12-2.5.0>kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application
```
`Note : You can run the above command from multiple command prompt windows and those acts as consumers to a praticular group.Each Consumer will read from a praticular partition`

- To read message as a consumer group from-beginning
```sh
E:\kafka_2.12-2.5.0>kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-second-application --from-beginning
```
`Note:Re running the above command shall not yeild the same results,this happens because of the concept called as consumer offsets`

#### Consumer Group
- To list available consumer groups
```sh
E:\kafka_2.12-2.5.0>kafka-consumer-groups --bootstrap-server localhost:9092 --list
```
- To describe a praticular group
```sh
E:\kafka_2.12-2.5.0>kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group my-first-application
```

- To reset offsets
```sh
E:\kafka_2.12-2.5.0>kafka-consumer-groups --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest  --execute --topic first_topic
```
```sh
E:\kafka_2.12-2.5.0>kafka-consumer-groups --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --shift-by -2 --execute --topic first_topic
```
---


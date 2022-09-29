 # Big Data Technology
 # Final Project 
 
 
 
technology used
- Apache Kafka
- ZooKeeper
- Apache Spark
 
``` shell

 
cd /home/cloudera/kafka_2.13-3.0.0/bin
sudo ./bin/zookeeper-server-start.sh config/zookeeper.properties

cd /home/cloudera/kafka_2.13-3.0.0/bin
./kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --topic tweets



```

 # Big Data Technology
 ### Final Project 
 
 
 
Technology used

- Apache Kafka
- ZooKeeper
- Apache Spark


![Design, archetictural](design.png)

``` shell

 
cd /home/cloudera/kafka_2.13-3.0.0/bin
sudo ./bin/zookeeper-server-start.sh config/zookeeper.properties

cd /home/cloudera/kafka_2.13-3.0.0/bin
./kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --topic tweets

hbase-shell.sh --create --bootstrap-server localhost:9092 --
list
get 'tweets', 'general-info';
count 'tweets'


```

Video Link [https://youtu.be/MbqYU7uRMNw]
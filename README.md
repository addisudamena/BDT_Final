# Big Data Technology Final Project

## Project Overview
This project demonstrates a big data pipeline integrating various technologies to ingest, process, and store Twitter data. The pipeline includes Apache Kafka for messaging, Apache Spark for real-time processing, and Apache HBase for data storage. This architecture showcases the power of distributed systems in handling large-scale data.

## Technologies Used
- **Apache Kafka**: A distributed messaging system for data streaming.
- **ZooKeeper**: A centralized service for maintaining configuration and state synchronization.
- **Apache Spark**: A distributed processing system for real-time data analytics.
- **Apache HBase**: A non-relational distributed database for large datasets.

---

## Pipeline Architecture
The following diagram represents the high-level architecture of the pipeline:

![Pipeline Design](design.png)

- **Twitter**: Acts as the data source, providing real-time tweets.
- **Kafka**: Consumes and streams the tweets.
- **Spark**: Processes the streamed tweets in real time.
- **HBase**: Stores the processed data for querying and analysis.

---

## Setup and Usage

### Step 1: Start ZooKeeper
ZooKeeper must be running before Kafka. Navigate to the Kafka directory and run the following command:
```shell
cd /home/cloudera/kafka_2.13-3.0.0/bin
sudo ./zookeeper-server-start.sh config/zookeeper.properties
```

### Step 2: Create Kafka Topic
Create a topic in Kafka named `tweets` to stream the data:
```shell
cd /home/cloudera/kafka_2.13-3.0.0/bin
./kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --topic tweets
```

### Step 3: Start HBase
Ensure HBase is running, then access the HBase shell:
```shell
hbase-shell
```

### Step 4: Perform Operations in HBase
You can execute the following commands to manage and query data in HBase:
```shell
list  # List all tables
get 'tweets', 'general-info'  # Retrieve data from the table
count 'tweets'  # Count rows in the table
```

---

## Video Demonstration
Watch the full project demonstration on YouTube:
[![Video Link](https://img.youtube.com/vi/yfANrj3HxBY/0.jpg)](https://youtu.be/yfANrj3HxBY)

---

## Future Work
- **Integration with Machine Learning Models**: Use processed tweets for sentiment analysis or topic modeling.
- **Dashboard Creation**: Build a real-time analytics dashboard using tools like Apache Superset or Grafana.
- **Scalability Testing**: Evaluate performance under high traffic and optimize configurations.

---

## License
This project is licensed under the MIT License. Feel free to use and modify it for educational purposes.

---

## Contact
For questions or collaborations, reach out to [Addisu Alemu](mailto:addisudalemu@gmail.com).


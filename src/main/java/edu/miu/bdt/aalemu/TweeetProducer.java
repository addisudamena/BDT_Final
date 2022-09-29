package edu.miu.bdt.aalemu;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.twitter.hbc.core.Client;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TweeetProducer {

	final Logger logger = LoggerFactory.getLogger(TweeetProducer.class);

	private Client client;
	private KafkaProducer<String, String> producer;
	private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(30);
	private static List<String> trackTerms;
	
	public static void main(String[] args) {
		trackTerms = Arrays.asList(args);
		new TweeetProducer().run();
	}



	private void run() {
		client = TweetClient.createTwitterClient(msgQueue, trackTerms);
		client.connect();

		Properties properties = new Properties();

		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				"localhost:9092");

		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class);

		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class);
		producer = new KafkaProducer<String, String>(properties);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			 	logger.info("Stopped");
				client.stop();
				producer.close();
			}));

		while (!client.isDone()) {
			String msg = null;
			try {
				msg = msgQueue.poll(TweetConfiguration.MESSAGE_TIMEOUT, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				client.stop();
			}
			if (msg != null) {
				JSONObject js = new JSONObject(msg);
				Tweet t = TweetClient.getTweet(js);

				producer.send(new ProducerRecord<String, String>(
						TweetConfiguration.TOPIC, "", new Gson().toJson(t)),
						new Callback() {
							@Override
							public void onCompletion(
									RecordMetadata recordMetadata, Exception e) {
								if (e != null) {
									 logger.error("Some error OR something bad happened",e);
								}
							}
						});
			}
		}
	}


}
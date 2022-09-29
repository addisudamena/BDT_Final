package edu.miu.bdt.aalemu;

public class 
TweetConfiguration {
	
	static final String TABLE_NAME = "tweets";
	static final String CF_DEFAULT = "tweet-info";
	static final String CF_GENERAL = "general-info";

	final static byte[] CF_DEFAULT_BYTES = CF_DEFAULT.getBytes();
	final static byte[] CF_GENERAL_BYTES = CF_GENERAL.getBytes();
	
	//Kafka configeration 
	 static final String BOOTSTRAPSERVERS  = "127.0.0.1:9092";
     public static final String TOPIC = "tweets";
     static final String BOOTSTRAP_SERVERS_CONFIG = "StringDeserializer";
     static final String  VALUE_DESERIALIZER_CLASS_CONFIG = "StringDeserializer";
	 static final long MESSAGE_TIMEOUT = 5;
	
}

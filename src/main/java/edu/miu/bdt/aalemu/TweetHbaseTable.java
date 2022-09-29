package edu.miu.bdt.aalemu;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;


public class TweetHbaseTable {
	
	public static Configuration config = HBaseConfiguration.create();
	public static Connection connection = null;
	
	public static Admin admin = null;
		
	private static Table tweets;
	
	static {
		try {
			connection = ConnectionFactory.createConnection(config);
			admin = connection.getAdmin();

			HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TweetConfiguration.TABLE_NAME));
			table.addFamily(new HColumnDescriptor(TweetConfiguration.CF_DEFAULT)
					.setCompressionType(Algorithm.NONE))
					.addFamily(new HColumnDescriptor(TweetConfiguration.CF_GENERAL)
					.setCompressionType(Algorithm.NONE));

			
			

			if (!admin.tableExists(table.getTableName())) {
				System.out.print("Creating table.");
				admin.createTable(table);
			}else{
				System.out.print("Table already exists.");
			}
			
			
			tweets = connection.getTable(TableName.valueOf(TweetConfiguration.TABLE_NAME));
			System.out.print("Connected");


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	public static void populateData(Tweet tweet) throws IOException {
		Put row = new Put(tweet.getId().getBytes());

		row.addColumn(TweetConfiguration.CF_DEFAULT_BYTES, "text".getBytes(), tweet.getText().getBytes());
		row.addColumn(TweetConfiguration.CF_DEFAULT_BYTES, "hashtags".getBytes(),String.join(", ", tweet.getHashTags()).getBytes());
		row.addColumn(TweetConfiguration.CF_DEFAULT_BYTES, "is_retweet".getBytes(), String.valueOf(tweet.isRetweet()).getBytes());

		if (tweet.getInReplyToStatusId() != null && "null".equals(tweet.getInReplyToStatusId()))
			row.addColumn(TweetConfiguration.CF_DEFAULT_BYTES, "reply_to".getBytes(), tweet.getInReplyToStatusId().getBytes());

		row.addColumn(TweetConfiguration.CF_GENERAL_BYTES, "username".getBytes(), tweet.getUsername().getBytes());
		row.addColumn(TweetConfiguration.CF_GENERAL_BYTES, "timestamp_ms".getBytes(), tweet.getTimeStamp().getBytes());
		row.addColumn(TweetConfiguration.CF_GENERAL_BYTES, "lang".getBytes(), tweet.getLang().getBytes());

		tweets.put(row);
	}
}
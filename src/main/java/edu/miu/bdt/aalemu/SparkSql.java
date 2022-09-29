package edu.miu.bdt.aalemu;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class SparkSql {;
	
	static Configuration config;
	static JavaSparkContext jsc;
	
	public static void main(String[] args) {

		SparkConf sconf = new SparkConf()
			.setAppName("SparkSQL")
			.setMaster("local[3]");
		
		sconf.registerKryoClasses(new Class[] { org.apache.hadoop.hbase.io.ImmutableBytesWritable.class });
		
		config = HBaseConfiguration.create();
		config.set(TableInputFormat.INPUT_TABLE, TweetConfiguration.TABLE_NAME);

		jsc = new JavaSparkContext(sconf);
		SQLContext sqlContext = new SQLContext(jsc.sc());
		
		JavaPairRDD<ImmutableBytesWritable, Result> hBaseRDD = readTableByJavaPairRDD();
		
		JavaRDD<Tweet> rows = hBaseRDD.map(x -> {
			Tweet tweet = new Tweet();
			
			tweet.setId(Bytes.toString(x._1.get()));
			tweet.setText(Bytes.toString(x._2.getValue(Bytes.toBytes(TweetConfiguration.CF_DEFAULT), Bytes.toBytes("text"))));
			tweet.setInReplyToStatusId(Bytes.toString(x._2.getValue(Bytes.toBytes(TweetConfiguration.CF_DEFAULT), Bytes.toBytes("inReplyToStatusId"))));

			tweet.setUsername(Bytes.toString(x._2.getValue(Bytes.toBytes(TweetConfiguration.CF_GENERAL), Bytes.toBytes("username"))));
			tweet.setTimeStamp(Bytes.toString(x._2.getValue(Bytes.toBytes(TweetConfiguration.CF_GENERAL), Bytes.toBytes("time_stamp"))));
			tweet.setLang(Bytes.toString(x._2.getValue(Bytes.toBytes(TweetConfiguration.CF_GENERAL), Bytes.toBytes("lang"))));

			return tweet;
		});
		
		

		DataFrame tabledata = sqlContext
				.createDataFrame(rows, Tweet.class);
		tabledata.registerTempTable(TweetConfiguration.TABLE_NAME);
		tabledata.printSchema();


		DataFrame query2 = sqlContext.sql("select username, count(*) from tweets group by username order by count(*) desc limit 10");
		query2.show();

		DataFrame query3 = sqlContext .sql("select inReplyToStatusId, count(*) from tweets GROUP BY inReplyToStatusId");
		 query3.show();		 

		 DataFrame query4 = sqlContext.sql("select lang, count(*) from tweets GROUP BY lang order by count(*) desc");
		 query4.show();	
		 
		jsc.stop();

	}
	
    public static JavaPairRDD<ImmutableBytesWritable, Result> readTableByJavaPairRDD() {
		
    	JavaPairRDD<ImmutableBytesWritable, Result> hBaseRDD = jsc
				.newAPIHadoopRDD(
						config,
						TableInputFormat.class,
						org.apache.hadoop.hbase.io.ImmutableBytesWritable.class,
						org.apache.hadoop.hbase.client.Result.class);
		return hBaseRDD;
    }
	

}

package edu.miu.bdt.aalemu;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TweetClient {

	// Twitter Client
	public static Client createTwitterClient(BlockingQueue<String> msgQueue,
			List<String> trackTerms) {
		/** Setting up a connection */
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hbEndpoint = new StatusesFilterEndpoint();
		// Term that I want to search on Twitter
		hbEndpoint.trackTerms(trackTerms);
		// Twitter API and tokens
		Authentication hosebirdAuth = new OAuth1(${consumerKey}, ${consumerSecret}, ${token, ${tokenSecret});

		/** Creating a client */
		ClientBuilder builder = new ClientBuilder().name("Hosebird-Client")
				.hosts(hosebirdHosts).authentication(hosebirdAuth)
				.endpoint(hbEndpoint)
				.processor(new StringDelimitedProcessor(msgQueue));

		Client hbClient = builder.build();

		return hbClient;
	}

	public static Tweet getTweet(JSONObject tweetObject) {

		System.out
				.println("[ - ]----------------------------------------------------------------------------------------------------");
		System.out.println(tweetObject.getString("id_str") + " : "
				+ tweetObject.getString("text"));

		Tweet tweet = new Tweet();

		tweet.setId(tweetObject.getString("id_str"));
		tweet.setText(tweetObject.getString("text"));
		tweet.setRetweet(tweet.getText().startsWith("RT @"));
		tweet.setInReplyToStatusId(tweetObject.get("in_reply_to_status_id")
				.toString());
		tweet.setUsername(tweetObject.getJSONObject("user").getString(
				"screen_name"));
		tweet.setTimeStamp(tweetObject.getString("timestamp_ms"));
		tweet.setLang(tweetObject.getString("lang"));

		JSONArray hasTags = tweetObject.getJSONObject("entities").getJSONArray(
				"hashtags");

		hasTags.forEach(tag -> {
			tweet.getHashTags().add(tag.toString());
		});

		return tweet;
	}

}

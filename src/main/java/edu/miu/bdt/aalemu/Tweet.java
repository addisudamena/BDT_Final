package edu.miu.bdt.aalemu;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

	private String id;

	private String text;
	private boolean isRetweet;
	private String inReplyToStatusId;

	private List<String> hashTags = new ArrayList<String>();

	private String username;

	private String timeStamp;
	private String lang;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRetweet() {
		return isRetweet;
	}

	public void setRetweet(boolean isRetweet) {
		this.isRetweet = isRetweet;
	}

	public String getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(String inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public List<String> getHashTags() {
		return hashTags;
	}

	public void setHashTags(List<String> hashTags) {
		this.hashTags = hashTags;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", text=" + text + ", isRetweet="
				+ isRetweet + ", inReplyToStatusId=" + inReplyToStatusId
				+ ", hashTags=" + hashTags + ", username=" + username
				+ ", timeStamp=" + timeStamp + ", lang=" + lang + "]";
	}

	
}

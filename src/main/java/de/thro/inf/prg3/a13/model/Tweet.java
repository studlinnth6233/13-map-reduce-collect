package de.thro.inf.prg3.a13.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import twitter4j.Status;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lightweight representation of a tweet
 *
 * @author Peter Kurfer
 */
public class Tweet
{
	/**
	 * RegEx pattern to extract the name of the client app out of the given HTML download link
	 */
	private static final Pattern clientPattern = Pattern.compile("^\\<[A-z =\":\\/\\.#!]+\\>([A-z ]+)\\<\\/a\\>$");

	/**
	 * Twitter-ID of the tweet
	 */
	private long id;

	/**
	 * Tweet text
	 */
	private String text;

	/**
	 * Source app which created the tweet as direct download link
	 */
	private String source;

	/**
	 * Short language identifier that describes in which language the tweet is written in
	 */
	private String language;

	/**
	 * Count of retweets
	 */
	private int retweetCount;

	/**
	 * Default constructor need for deserialization
	 */
	public Tweet() { }

	/**
	 * Constructor overload used by the factory method
	 */
	private Tweet(long id, String text, String source, String language, int retweetCount)
	{
		this.id = id;
		this.text = text;
		this.source = source;
		this.language = language;
		this.retweetCount = retweetCount;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public int getRetweetCount()
	{
		return retweetCount;
	}

	public void setRetweetCount(int retweetCount)
	{
		this.retweetCount = retweetCount;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;

		if (!(o instanceof Tweet)) return false;

		Tweet tweet = (Tweet) o;

		return new EqualsBuilder()
			.append(getId(), tweet.getId())
			.append(getRetweetCount(), tweet.getRetweetCount())
			.append(getText(), tweet.getText())
			.append(getSource(), tweet.getSource())
			.append(getLanguage(), tweet.getLanguage())
			.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 37)
			.append(getId())
			.append(getText())
			.append(getSource())
			.append(getLanguage())
			.append(getRetweetCount())
			.toHashCode();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
			.append("id", id)
			.append("text", text)
			.append("source", source)
			.append("language", language)
			.append("retweetCount", retweetCount)
			.toString();
	}

	/**
	 * Extracts the actual name of the source app from the given download link
	 *
	 * @return plain source app name as String or 'Unknown' if the name cannot be extracted
	 */
	public String getSourceApp()
	{
		if (source == null || source.length() == 0) return "Unknown";

		Matcher m = clientPattern.matcher(source);

		if (m.matches() && m.groupCount() >= 1)
			return m.group(1);

		return "Unknown";
	}

	/**
	 * Factory method to create a tweet instance from the corresponding Twitter4j Status object
	 *
	 * @param status Twitter4j status wrapper instance
	 *
	 * @return Extracted Tweet object
	 *
	 * @throws NullPointerException if status is null
	 */
	public static Tweet fromStatus(Status status)
	{
		Objects.requireNonNull(status);

		return new Tweet(status.getId(), status.getText(), status.getSource(), status.getLang(), status.getRetweetCount());
	}
}

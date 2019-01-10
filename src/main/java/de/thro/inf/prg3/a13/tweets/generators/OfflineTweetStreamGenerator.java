package de.thro.inf.prg3.a13.tweets.generators;

import com.google.gson.Gson;
import de.thro.inf.prg3.a13.model.Tweet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Stream generator based on local JSON file
 *
 * @author Peter Kurfer
 */
public final class OfflineTweetStreamGenerator implements TweetStreamGenerator {

	private static final String TRUMP_TWEETS_JSON_PATH = "/trump_tweets.json";

	/**
	 * Gson instance to deserialize JSON file
	 */
	private final Gson gson;

	/**
	 * Default constructor
	 */
	public OfflineTweetStreamGenerator() {
		this.gson = new Gson();
	}

	/**
	 * Creates new stream of Tweets by reading and deserializing `trump_tweets.json`
	 *
	 * @return new Stream of Tweets or empty Stream if problem occurred
	 */
	@Override
	public Stream<Tweet> getTweetStream() {
		/* try-with-resources */
		try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(TRUMP_TWEETS_JSON_PATH))){
			/* create new Stream of deserialized array of Tweets */
			return Arrays.stream(gson.fromJson(reader, Tweet[].class));
		}catch (IOException e){
			return Stream.of();
		}
	}
}

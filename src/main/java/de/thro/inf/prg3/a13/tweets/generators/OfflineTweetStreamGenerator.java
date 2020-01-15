package de.thro.inf.prg3.a13.tweets.generators;

import com.google.gson.Gson;
import de.thro.inf.prg3.a13.model.Tweet;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

public class OfflineTweetStreamGenerator implements TweetStreamGenerator
{
	private Gson gson;

	public OfflineTweetStreamGenerator()
	{
		this.gson = new Gson();
	}

	@Override
	public Stream<Tweet> getTweetStream()
	{
		InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/trump_tweets.json"));

		return Arrays.stream(gson.fromJson(reader, Tweet[].class));
	}
}

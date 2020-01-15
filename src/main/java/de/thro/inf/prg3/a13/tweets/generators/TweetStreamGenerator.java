package de.thro.inf.prg3.a13.tweets.generators;

import de.thro.inf.prg3.a13.model.Tweet;

import java.util.stream.Stream;

/**
 * Generator for a Stream of tweets
 *
 * @author Peter Kurfer
 */
public interface TweetStreamGenerator
{
	/**
	 * Get a new Stream of tweets
	 *
	 * @return Stream of tweets - length may be unknown
	 */
	Stream<Tweet> getTweetStream();
}

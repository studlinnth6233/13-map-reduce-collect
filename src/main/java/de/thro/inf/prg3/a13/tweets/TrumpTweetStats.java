package de.thro.inf.prg3.a13.tweets;

import de.thro.inf.prg3.a13.model.Lazy;
import de.thro.inf.prg3.a13.model.Tweet;
import de.thro.inf.prg3.a13.tweets.generators.TweetStreamGenerator;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.thro.inf.prg3.a13.model.Lazy.lazyOf;

/**
 * @author Peter Kurfer
 */
public class TrumpTweetStats {

	/**
	 * Pattern to filter for words that are only alpha numerical
	 */
	private static final Pattern ALPHA_NUMERICAL = Pattern.compile("[a-z0-9@]+");

	/**
	 * Generator instance as for the calculation of every metric a new Stream is required
	 */
	private final TweetStreamGenerator streamGenerator;

	/**
	 * Lazy wrapper for the result of the word count calculation
	 */
	private final Lazy<Map<String, Integer>> wordCountLazy;

	/**
	 * Lazy wrapper for the result of the source app stats calculation
	 */
	private final Lazy<Map<String, Long>> sourceAppStatsLazy;

	/**
	 * Lazy wrapper for the result of the tweets by source app calculation
	 */
	private final Lazy<Map<String, Set<Tweet>>> tweetsBySourceAppLazy;

	public TrumpTweetStats(TweetStreamGenerator streamGenerator, Set<String> stopWords) {
		this.streamGenerator = streamGenerator;
		wordCountLazy = lazyOf(() -> calculateWordCount(this.streamGenerator.getTweetStream(), stopWords));
		sourceAppStatsLazy = lazyOf(() -> calculateSourceAppStats(this.streamGenerator.getTweetStream()));
		tweetsBySourceAppLazy = lazyOf(() -> calculateTweetsBySourceApp(this.streamGenerator.getTweetStream()));
	}

	/**
	 * Calculate the word count of a given Stream of tweets
	 *
	 * @param tweetStream Stream of tweets
	 * @param stopWords   List of words to ignore
	 * @return word count result
	 */
	public static Map<String, Integer> calculateWordCount(Stream<Tweet> tweetStream, Set<String> stopWords) {
		// -> current type Stream<Tweet>
		return tweetStream
			// extract the tweet text -> current type Stream<String>
			.map(Tweet::getText)
			// split tweet text at ' ' -> current type Stream<String[]>
			.map(t -> t.split("( )+"))
			// use `flatMap` to map the arrays to a single Stream -> current type Stream<String>
			.flatMap(Arrays::stream)
			// map all Strings to lowercase -> current type Stream<String>
			.map(String::toLowerCase)
			// filter for only alpha numerical words -> current type Stream<String>
			.filter(word -> ALPHA_NUMERICAL.matcher(word).matches())
			// filter to remove stop words -> current type Stream<String>
			.filter(w -> stopWords.stream().noneMatch(sw -> sw.equals(w)))
			// reduce the Stream to Map<String, Integer>
			.reduce(new LinkedHashMap<String, Integer>(), (acc, word) -> {
				/* put the current word to the map - overwrites entry but computes new value first
				 * by calling `.compute` on the map that passes the current value or null to the lambda depending if the key already exists or not */
				acc.put(word, acc.compute(word, (k, v) -> v == null ? 1 : v + 1));
				return acc;
			}, (m1, m2) -> m1) /* Map<String, Long> */
			/* get entry set of the resulting map and create a new Stream */
			.entrySet()
			.stream()
			/* filter for words that occurred less than 10 times */
			.filter(entry -> entry.getValue() > 10)
			/* collect them once more in a map */
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	/**
	 * Calculate how many tweets were created with which app
	 *
	 * @param tweetStream Stream of tweets to process
	 * @return source app stats
	 */
	public static Map<String, Long> calculateSourceAppStats(Stream<Tweet> tweetStream) {
		return tweetStream
			/* transform every Tweet object to the plain source app name */
			.map(Tweet::getSourceApp)
			/* collect the tweets in a map of String and Integer - short form of map-reduce */
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	/**
	 * Calculate how many tweets were created with which app - implemented with Map-Reduce
	 *
	 * @param tweetStream Stream of tweets to process
	 * @return source app stats
	 */
	public static Map<String, Long> calculateSourceAppStatsWithReduce(Stream<Tweet> tweetStream) {
		return tweetStream
			/* transform every Tweet object to the plain source app name */
			.map(Tweet::getSourceApp)
			/* reduce the stream like in the word count implementation */
			.reduce(new LinkedHashMap<String, Long>(), (acc, app) -> {
				acc.put(app, acc.compute(app, (k, v) -> v == null ? 1 : v + 1));
				return acc;
			}, (m1, m2) -> m1);
	}

	/**
	 * Group the tweets by the source app they were created with
	 *
	 * @param tweetStream Stream of tweets to process
	 * @return Map of source app string and all tweets that were created with this app
	 */
	public static Map<String, Set<Tweet>> calculateTweetsBySourceApp(Stream<Tweet> tweetStream) {
		return tweetStream
			/* collect with default `groupingBy` but replace the value collector */
			.collect(Collectors.groupingBy(Tweet::getSourceApp, Collectors.toSet()));
	}

	/**
	 * Group the tweets by the source app they were created with - implemented with Map-Reduce
	 *
	 * @param tweetStream Stream of tweets to process
	 * @return Map of source app string and all tweets that were created with this app
	 */
	public static Map<String, Set<Tweet>> calculateTweetsBySourceAppWithReduce(Stream<Tweet> tweetStream) {
		return tweetStream
			/* single reduce step implemented as in word count as java does not support tuples out of the box
			 * and that's the cleanest way to implement it */
			.reduce(new LinkedHashMap<String, Set<Tweet>>(), (acc, tweet) -> {
				/* insert new set if required or update existing set */
				Set<Tweet> tweets = acc.containsKey(tweet.getSourceApp()) ? acc.get(tweet.getSourceApp()) : new HashSet<>();
				tweets.add(tweet);
				acc.put(tweet.getSourceApp(), tweets);
				return acc;
			}, (m1, m2) -> m1);
	}

	public Map<String, Integer> getWordCount() {
		return wordCountLazy.getValue();
	}

	public Map<String, Long> getSourceAppStats() {
		return sourceAppStatsLazy.getValue();
	}

	public Map<String, Set<Tweet>> getTweetsBySourceApp() {
		return tweetsBySourceAppLazy.getValue();
	}
}

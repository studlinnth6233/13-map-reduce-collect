package de.thro.inf.prg3.a13.tweets;

import de.thro.inf.prg3.a13.model.Tweet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;

import static de.thro.inf.prg3.a13.utils.PrintUtils.iterableToString;
import static de.thro.inf.prg3.a13.utils.PrintUtils.mapToString;
import static de.thro.inf.prg3.a13.utils.ResourceUtils.loadStopWordsFunctional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author Peter Kurfer
 * Created on 1/14/18.
 */
class TrumpTweetStatsTest {

	private static final Logger logger = Logger.getLogger(TrumpTweetStatsTest.class.getName());

	private final TweetStreamFactory tweetStreamFactory;
	private final Set<String> stopWords;

	TrumpTweetStatsTest() {
		this.tweetStreamFactory = TweetStreamFactory.getInstance();
		this.stopWords = loadStopWordsFunctional();
	}

	@ParameterizedTest
	@ValueSource(strings = {"ONLINE", "OFFLINE"})
	void getTweetsBySourceApp(String tweetSourceName) {
		var tweetSource = TweetSource.valueOf(tweetSourceName);

		var stats = new TrumpTweetStats(tweetStreamFactory.getStreamGenerator(tweetSource), stopWords);
		var tweetsBySourceApp = stats.getTweetsBySourceApp();

		assertNotEquals(0, tweetsBySourceApp.keySet().size());
		logger.info(mapToString(tweetsBySourceApp, Function.identity(), set -> iterableToString(set, Tweet::getText)));
	}

	@ParameterizedTest
	@ValueSource(strings = {"ONLINE", "OFFLINE"})
	void getWordCount(String tweetSourceName) {
		var tweetSource = TweetSource.valueOf(tweetSourceName);

		var stats = new TrumpTweetStats(tweetStreamFactory.getStreamGenerator(tweetSource), stopWords);

		var wordCount = stats.getWordCount();
		assertNotEquals(0, wordCount.keySet().size());
		logger.info(mapToString(wordCount));
	}

	@ParameterizedTest
	@ValueSource(strings = {"ONLINE", "OFFLINE"})
	void getSourceAppStats(String tweetSourceName) {
		var tweetSource = TweetSource.valueOf(tweetSourceName);

		var stats = new TrumpTweetStats(tweetStreamFactory.getStreamGenerator(tweetSource), stopWords);

		var sourceAppStats = stats.getSourceAppStats();
		assertNotEquals(0, sourceAppStats.keySet().size());
		logger.info(mapToString(sourceAppStats));
	}

	@ParameterizedTest
	@ValueSource(strings = {"ONLINE", "OFFLINE"})
	void calculateSourceAppStatsWithReduce(String tweetSourceName) {
		var tweetSource = TweetSource.valueOf(tweetSourceName);
		var generator = tweetStreamFactory.getStreamGenerator(tweetSource);
		var withoutReduce = TrumpTweetStats.calculateSourceAppStats(generator.getTweetStream());
		var withReduce = TrumpTweetStats.calculateSourceAppStatsWithReduce(generator.getTweetStream());

		assertEquals(withoutReduce.keySet().size(), withReduce.keySet().size());

		assertEquals(withoutReduce.keySet(), withReduce.keySet());
	}

	@ParameterizedTest
	@ValueSource(strings = {"ONLINE", "OFFLINE"})
	void calculateTweetsBySourceAppWithReduce(String tweetSourceName) {
		var tweetSource = TweetSource.valueOf(tweetSourceName);
		var generator = tweetStreamFactory.getStreamGenerator(tweetSource);

		var withoutReduce = TrumpTweetStats.calculateTweetsBySourceApp(generator.getTweetStream());
		var withReduce = TrumpTweetStats.calculateTweetsBySourceAppWithReduce(generator.getTweetStream());

		assertEquals(withoutReduce.keySet(), withReduce.keySet());

	}
}

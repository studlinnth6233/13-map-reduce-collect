package de.thro.inf.prg3.a13.tweets;

import de.thro.inf.prg3.a13.model.Tweet;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the Tweet stream generators
 *
 * @author Peter Kurfer
 */
class TwitterStreamSourceTest
{
	private static final Logger logger = Logger.getLogger(TwitterStreamSourceTest.class.getName());
	private final TweetStreamFactory tweetStreamFactory;

	TwitterStreamSourceTest()
	{
		tweetStreamFactory = TweetStreamFactory.getInstance();
	}

	@Test
	void getTweetsOnlineWithLimit()
	{
		if (!tweetStreamFactory.isOnlineAvailable())
			logger.info("Skipping online test 'testGetTweetsOnlineWithLimit' because Twitter4j is not configured");

		Stream<Tweet> tweetStream = tweetStreamFactory.getStreamGenerator(TweetSource.ONLINE)
			.getTweetStream();

		List<String> tweetTexts = tweetStream.limit(300)
			.map(Tweet::getText)
			.collect(Collectors.toList());

		assertEquals(300, tweetTexts.size());

		for (String s : tweetTexts)
			logger.info(s);
	}

	@Test
	void getTweetsOnlineWithoutLimit()
	{
		if (!tweetStreamFactory.isOnlineAvailable())
			logger.info("Skipping online test 'testGetTweetsOnlineWithoutLimit' because Twitter4j is not configured");

		assertTimeout(Duration.ofMinutes(5), () ->
		{
			List<Tweet> tweets = tweetStreamFactory.getStreamGenerator(TweetSource.ONLINE)
				.getTweetStream()
				.collect(Collectors.toList());

			assertNotNull(tweets);
			logger.info(String.format("Fetched %d tweets", tweets.size()));
		});
	}

	@Test
	void getTweetsOffline()
	{
		List<String> tweets = tweetStreamFactory.getStreamGenerator()
			.getTweetStream()
			.map(Tweet::getText)
			.collect(Collectors.toList());

		assertNotNull(tweets);
		assertEquals(3225, tweets.size());
		assertTrue(tweets.stream().noneMatch(Objects::isNull));
	}
}

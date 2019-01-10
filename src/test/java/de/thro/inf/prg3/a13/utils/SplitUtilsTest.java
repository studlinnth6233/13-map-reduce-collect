package de.thro.inf.prg3.a13.utils;

import de.thro.inf.prg3.a13.model.Tweet;
import de.thro.inf.prg3.a13.tweets.TweetSource;
import de.thro.inf.prg3.a13.tweets.TweetStreamFactory;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author Peter Kurfer
 * Created on 1/17/18.
 */
class SplitUtilsTest {

	private static final Logger logger = Logger.getLogger(SplitUtilsTest.class.getName());

	private final TweetStreamFactory tweetStreamFactory;

	public SplitUtilsTest() {
		this.tweetStreamFactory = TweetStreamFactory.getInstance();
	}

	@Test
	void splitTweetText() {
		Stream<Tweet> tweetStream = tweetStreamFactory.getStreamGenerator(TweetSource.OFFLINE).getTweetStream();
		SplitUtils.splitTweetText(tweetStream)
			.forEach(word -> {
				assertNotEquals(" ", word);
				logger.info(word);
			});
	}
}

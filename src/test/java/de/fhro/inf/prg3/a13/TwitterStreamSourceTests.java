package de.fhro.inf.prg3.a13;

import de.fhro.inf.prg3.a13.model.Tweet;
import de.fhro.inf.prg3.a13.tweets.TweetSource;
import de.fhro.inf.prg3.a13.tweets.TweetStreamFactory;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Peter Kurfer
 * Created on 1/11/18.
 */
class TwitterStreamSourceTests {

    private static final Logger logger = Logger.getLogger(TwitterStreamSourceTests.class.getName());
    private final TweetStreamFactory tweetStreamFactory;

    TwitterStreamSourceTests() {
        tweetStreamFactory = TweetStreamFactory.getInstance();
    }

    @Test
    void testGetTweetsOnlineWithLimit() {
        if (!tweetStreamFactory.isOnlineAvailable())
            logger.info("Skipping online test 'testGetTweetsOnlineWithLimit' because Twitter4j is not configured");
        Stream<Tweet> tweetStream = tweetStreamFactory.getTweetsStream(TweetSource.ONLINE);

        List<String> tweetTexts = tweetStream.limit(300)
                .map(Tweet::getText)
                .collect(Collectors.toList());

        assertEquals(300, tweetTexts.size());

        for (String s : tweetTexts) {
            logger.info(s);
        }
    }

    @Test
    void testGetTweetsOnlineWithoutLimit() {
        if (!tweetStreamFactory.isOnlineAvailable())
            logger.info("Skipping online test 'testGetTweetsOnlineWithoutLimit' because Twitter4j is not configured");
        assertTimeout(Duration.ofMinutes(5), () -> {
            List<Tweet> tweets = tweetStreamFactory.getTweetsStream(TweetSource.ONLINE)
                    .collect(Collectors.toList());

            assertNotNull(tweets);
            logger.info(String.format("Fetched %d tweets", tweets.size()));
        });
    }

    @Test
    void testGetTweetsOffline() {
        List<String> tweets = tweetStreamFactory.getTweetsStream()
                .map(Tweet::getText)
                .collect(Collectors.toList());

        assertNotNull(tweets);
        assertEquals(3225, tweets.size());
        assertTrue(tweets.stream().noneMatch(Objects::isNull));
    }
}

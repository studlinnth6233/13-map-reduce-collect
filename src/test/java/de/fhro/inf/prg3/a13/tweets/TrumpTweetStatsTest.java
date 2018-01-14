package de.fhro.inf.prg3.a13.tweets;

import de.fhro.inf.prg3.a13.model.Tweet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static de.fhro.inf.prg3.a13.utils.PrintUtils.iterableToString;
import static de.fhro.inf.prg3.a13.utils.PrintUtils.mapToString;
import static de.fhro.inf.prg3.a13.utils.ResourceUtils.loadStopWords;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test cases for TrumpTweetStats
 * @author Peter Kurfer
 */
class TrumpTweetStatsTest {

    private static final Logger logger = Logger.getLogger(TrumpTweetStatsTest.class.getName());

    private final TweetStreamFactory tweetStreamFactory;
    private final List<String> stopWords;

    TrumpTweetStatsTest() {
        /* get singleton instance of generator factory */
        this.tweetStreamFactory = TweetStreamFactory.getInstance();
        /* load stop words */
        this.stopWords = loadStopWords();
    }

    /**
     * Test of `calculateTweetsBySourceApp`
     * if you have configured Twitter4j you can change the ValueSource annotation to @ValueSource(strings = {"ONLINE", "OFFLINE"}) to include online tests
     */
    @ParameterizedTest
    @ValueSource(strings = {"OFFLINE"})
    void calculateTweetsBySourceApp(String tweetSourceName) {
        TweetSource tweetSource = TweetSource.valueOf(tweetSourceName);

        Stream<Tweet> tweetStream = tweetStreamFactory.getStreamGenerator(tweetSource).getTweetStream();
        Map<String, Set<Tweet>> tweetsBySourceApp = TrumpTweetStats.calculateTweetsBySourceApp(tweetStream);

        assertNotEquals(0, tweetsBySourceApp.keySet().size());
        logger.info(mapToString(tweetsBySourceApp, Function.identity(), set -> iterableToString(set, Tweet::getText)));
    }

    /**
     * Test of `calculateWordCount`
     * if you have configured Twitter4j you can change the ValueSource annotation to @ValueSource(strings = {"ONLINE", "OFFLINE"}) to include online tests
     */
    @ParameterizedTest
    @ValueSource(strings = {"OFFLINE"})
    void getWordCount(String tweetSourceName) {
        TweetSource tweetSource = TweetSource.valueOf(tweetSourceName);

        Stream<Tweet> tweetStream = tweetStreamFactory.getStreamGenerator(tweetSource).getTweetStream();
        Map<String, Integer> wordCount = TrumpTweetStats.calculateWordCount(tweetStream, stopWords);
        assertNotEquals(0, wordCount.keySet().size());
        logger.info(mapToString(wordCount));
    }

    /**
     * Test of `calculateSourceAppStats`
     * if you have configured Twitter4j you can change the ValueSource annotation to @ValueSource(strings = {"ONLINE", "OFFLINE"}) to include online tests
     */
    @ParameterizedTest
    @ValueSource(strings = {"OFFLINE"})
    void calculateSourceAppStats(String tweetSourceName) {
        TweetSource tweetSource = TweetSource.valueOf(tweetSourceName);

        Stream<Tweet> tweetStream = tweetStreamFactory.getStreamGenerator(tweetSource).getTweetStream();
        Map<String, Long> sourceAppStats = TrumpTweetStats.calculateSourceAppStats(tweetStream);
        assertNotEquals(0, sourceAppStats.keySet().size());
        logger.info(mapToString(sourceAppStats));
    }
}
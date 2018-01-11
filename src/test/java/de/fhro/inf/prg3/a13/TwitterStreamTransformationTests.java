package de.fhro.inf.prg3.a13;

import de.fhro.inf.prg3.a13.model.Tweet;
import de.fhro.inf.prg3.a13.tweets.TweetSource;
import de.fhro.inf.prg3.a13.tweets.TweetStreamFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Peter Kurfer
 * Created on 1/11/18.
 */
class TwitterStreamTransformationTests {

    private final TweetStreamFactory tweetStreamFactory;

    public TwitterStreamTransformationTests() {
        tweetStreamFactory = TweetStreamFactory.getInstance();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ONLINE", "OFFLINE"})
    void testGroupBySource(String tweetSourceName) {
        TweetSource tweetSource = TweetSource.valueOf(tweetSourceName);

        Stream<Tweet> tweetStream = tweetStreamFactory.getTweetsStream(tweetSource);

        /* TODO implement grouping by */
        fail("Not yet implemented");
    }
}

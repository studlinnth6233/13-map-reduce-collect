package de.fhro.inf.prg3.a13;

import de.fhro.inf.prg3.a13.model.Tweet;
import de.fhro.inf.prg3.a13.tweets.TweetSource;
import de.fhro.inf.prg3.a13.tweets.TweetStreamGenerator;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

/**
 * @author Peter Kurfer
 * Created on 1/11/18.
 */
class TwitterStreamTests {

    @Test
    void testGetTweetsOnline() {
        Stream<Tweet> tweetStream = TweetStreamGenerator.getTweetsStream(TweetSource.ONLINE);
    }
}

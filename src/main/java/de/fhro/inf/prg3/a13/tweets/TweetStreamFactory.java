package de.fhro.inf.prg3.a13.tweets;

import de.fhro.inf.prg3.a13.model.Tweet;
import de.fhro.inf.prg3.a13.tweets.generators.OnlineTweetStreamGenerator;
import de.fhro.inf.prg3.a13.tweets.generators.TweetStreamGenerator;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Factory singleton to create tweet streams
 * @author Peter Kurfer
 */
public class TweetStreamFactory {

    private static final TweetStreamFactory instance = new TweetStreamFactory();

    private final boolean isTwitter4jConfigured;
    private final TweetStreamGenerator onlineTweetStreamGenerator;

    private TweetStreamFactory() {
        boolean configured = false;
        onlineTweetStreamGenerator = new OnlineTweetStreamGenerator();
        try {
            Properties twitter4jProps = new Properties();

            /* load properties to determine if Twitter4j is configured */
            twitter4jProps.load(TweetStreamFactory.class.getResourceAsStream("/twitter4j.properties"));

            /* filter properties for value '<dummy>' as in the initial state */
            configured = twitter4jProps.stringPropertyNames()
                    .stream()
                    .map(twitter4jProps::getProperty)
                    .noneMatch(value -> value.equals("<dummy>"));

        } catch (IOException e) {
            configured = false;
        } finally {
            isTwitter4jConfigured = configured;
        }
    }

    /**
     * Singleton accessor
     * @return singleton instance
     */
    public static TweetStreamFactory getInstance() {
        return instance;
    }

    /**
     * Determine if Twitter4j is configured correctly
     */
    public boolean isOnlineAvailable() {
        return isTwitter4jConfigured;
    }

    /**
     * Get a new stream of Tweets
     * @param tweetSource indicator which source of Tweets to use
     */
    public Stream<Tweet> getTweetsStream(TweetSource tweetSource) {
        if (tweetSource == TweetSource.ONLINE && isTwitter4jConfigured) {
            return onlineTweetStreamGenerator.getTweetStream();
        }
        /* TODO use offline source */
        throw new NotImplementedException("TweetStreamFactory.getTweetsStream() is not implemented yet");
    }

    /**
     * Get a new stream of Tweets from offline source
     */
    public Stream<Tweet> getTweetsStream() {
        return getTweetsStream(TweetSource.OFFLINE);
    }
}

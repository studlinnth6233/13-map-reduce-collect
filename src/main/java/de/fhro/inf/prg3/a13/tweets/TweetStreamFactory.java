package de.fhro.inf.prg3.a13.tweets;

import de.fhro.inf.prg3.a13.model.Tweet;
import de.fhro.inf.prg3.a13.tweets.generators.OnlineTweetStreamGenerator;
import de.fhro.inf.prg3.a13.tweets.generators.TweetStreamGenerator;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

/**
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
            twitter4jProps.load(TweetStreamFactory.class.getResourceAsStream("/twitter4j.properties"));

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

    public static TweetStreamFactory getInstance() {
        return instance;
    }

    public boolean isOnlineAvailable() {
        return isTwitter4jConfigured;
    }

    public Stream<Tweet> getTweetsStream(TweetSource tweetSource) {
        if (tweetSource == TweetSource.ONLINE && isTwitter4jConfigured) {
            return onlineTweetStreamGenerator.getTweetStream();
        }
        /* TODO use offline source */
        throw new NotImplementedException("TweetStreamFactory.getTweetsStream() is not implemented yet");
    }

    public Stream<Tweet> getTweetsStream() {
        return getTweetsStream(TweetSource.OFFLINE);
    }
}

package de.fhro.inf.prg3.a13.tweets;

import de.fhro.inf.prg3.a13.model.Tweet;

import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * @author Peter Kurfer
 */
public abstract class TweetStreamGenerator {

    private static final boolean isTwitter4jConfigured;

    private TweetStreamGenerator() {
    }

    static {
        boolean configured = false;
        try {
            Properties twitter4jProps = new Properties();
            twitter4jProps.load(TweetStreamGenerator.class.getResourceAsStream("/twitter4j.properties"));
            configured = twitter4jProps.stringPropertyNames().stream().map(twitter4jProps::getProperty).noneMatch(value -> value.equals("<dummy>"));
        } catch (IOException e) {
            configured = false;
        }finally {
            isTwitter4jConfigured = configured;
        }
    }

    public static Stream<Tweet> getTweetsStream(TweetSource tweetSource) {
        if(tweetSource == TweetSource.ONLINE && isTwitter4jConfigured) {
            /* use online source */
        }
        /* use offline source */
        return null;
    }
}

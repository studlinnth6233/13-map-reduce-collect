package de.fhro.inf.prg3.a13.tweets.generators;

import de.fhro.inf.prg3.a13.model.Tweet;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Peter Kurfer
 * Created on 1/11/18.
 */
public class OnlineTweetStreamGenerator implements TweetStreamGenerator {

    private static final String TWITTER_SCREEN_NAME = "realDonaldTrump";
    private static final int PAGE_SIZE = 200;

    private final Twitter twitter;

    public OnlineTweetStreamGenerator() {
        twitter = TwitterFactory.getSingleton();
    }

    @Override
    public Stream<Tweet> getTweetStream() {
        final List<Tweet> currentTweetResult = getPageOfTweets(1);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<Tweet>() {

            private int currentOffset;
            private int currentPage = 1;

            @Override
            public boolean hasNext() {
                if(currentOffset + 1 == currentTweetResult.size()) {
                    currentTweetResult.clear();
                    List<Tweet> newTweets = getPageOfTweets(++currentPage);
                    currentTweetResult.addAll(newTweets);
                    currentOffset = -1;
                }
                return currentTweetResult.size() > 0;
            }

            @Override
            public Tweet next() {
                return currentTweetResult.get(++currentOffset);
            }
        }, Spliterator.DISTINCT | Spliterator.IMMUTABLE), false);
    }

    private List<Tweet> getPageOfTweets(int index) {
        try {
            return twitter.timelines().getUserTimeline(TWITTER_SCREEN_NAME, new Paging(index, PAGE_SIZE)).stream().map(Tweet::fromStatus).collect(Collectors.toList());
        } catch (TwitterException e) {
            return new LinkedList<>();
        }
    }
}

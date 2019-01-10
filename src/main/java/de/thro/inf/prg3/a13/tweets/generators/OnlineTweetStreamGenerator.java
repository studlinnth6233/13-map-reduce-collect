package de.thro.inf.prg3.a13.tweets.generators;

import de.thro.inf.prg3.a13.model.Tweet;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Stream generator which loads tweets live from the Twitter API
 * @author Peter Kurfer
 */
public final class OnlineTweetStreamGenerator implements TweetStreamGenerator {

	private static final String TWITTER_SCREEN_NAME = "realDonaldTrump";
	private static final int PAGE_SIZE = 200;

	/**
	 * Twitter4j instance to access Twitter API
	 */
	private final Twitter twitter;

	public OnlineTweetStreamGenerator() {
		twitter = TwitterFactory.getSingleton();
	}

	/**
	 * @return Stream of tweets live from the Twitter API - length is unknown as it depends on how many tweets are kept outside the archive at twitter
	 */
	@Override
	public final Stream<Tweet> getTweetStream() {
		/* fetch initial page of tweets */
		final List<Tweet> currentTweetResult = getPageOfTweets(1);

		/* create a new Stream based on an iterator of unknown length */
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<Tweet>() {

			/**
			 * Offset of the iterator on the current page
			 */
			private int currentOffset = -1;

			/**
			 * Index of the current page at the Twitter API
			 */
			private int currentPage = 1;

			@Override
			public boolean hasNext() {
				/* if current page was entirely iterated fetch next page */
				if(currentOffset + 1 == currentTweetResult.size()) {
					/* as the instance is final it has to be cleared instead of an overwrite */
					currentTweetResult.clear();
					/* fetch next page - list will be empty if an error occurred */
					List<Tweet> newTweets = getPageOfTweets(++currentPage);

					/* Add the fetched tweets to the instance */
					currentTweetResult.addAll(newTweets);

					/* reset offset */
					currentOffset = -1;
				}
				return currentTweetResult.size() > 0;
			}

			@Override
			public Tweet next() {
				/* increment offset and get element of current page */
				return currentTweetResult.get(++currentOffset);
			}
			/* characteristics the stream fulfills */
		}, Spliterator.DISTINCT | Spliterator.IMMUTABLE), false);
	}

	/**
	 * Fetch a page of tweets
	 * @param index index of the page to fetch
	 * @return List of tweets - list will be empty if there are no more tweets or an error occurred
	 */
	private List<Tweet> getPageOfTweets(int index) {
		try {

			return twitter.timelines()
				/* get the page of tweets specified as Paging object */
				.getUserTimeline(TWITTER_SCREEN_NAME, new Paging(index, PAGE_SIZE))
				.stream()
				/* map Status object to Tweet object */
				.map(Tweet::fromStatus)
				/* collect elements as list */
				.collect(Collectors.toList());

		} catch (TwitterException e) {
			return new LinkedList<>();
		}
	}
}

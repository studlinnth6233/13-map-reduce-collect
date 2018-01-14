package de.fhro.inf.prg3.a13.tweets;

import de.fhro.inf.prg3.a13.model.Tweet;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Peter Kurfer
 */
public class TrumpTweetStats {

    public static Map<String, Long> calculateSourceAppStats(Stream<Tweet> tweetStream) {
        /* TODO group the tweets by the `sourceApp` they were created with and count how many it were per `sourceApp` */
        throw new NotImplementedException("TrumpTweetStats.calculateSourceAppStats(...) not implemented yet.");
    }

    public static Map<String, Set<Tweet>> calculateTweetsBySourceApp(Stream<Tweet> tweetStream) {
        /* TODO group the tweets by the `sourceApp`
         * collect the tweets in `Set`s for each source app */
        throw new NotImplementedException("TrumpTweetStats.calculateTweetsBySourceApp(...) not implemented yet.");
    }

    public static Map<String, Integer> calculateWordCount(Stream<Tweet> tweetStream, List<String> stopWords) {
        /* Remark: implement this method at last */
        /* TODO split the tweets, lower them, trim them, remove all words that are in the `stopWords`,
         * reduce the result to a Map<String, Integer> to count how often each word were in the tweets
         * optionally you could filter for all words that were used more than 10 times */
        throw new NotImplementedException("TrumpTweetStats.tweetStream(...) not implemented yet.");
    }
}

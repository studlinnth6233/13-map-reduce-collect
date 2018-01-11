package de.fhro.inf.prg3.a13.tweets.generators;

import de.fhro.inf.prg3.a13.model.Tweet;

import java.util.stream.Stream;

/**
 * @author Peter Kurfer
 * Created on 1/11/18.
 */
public interface TweetStreamGenerator {

    Stream<Tweet> getTweetStream();
}

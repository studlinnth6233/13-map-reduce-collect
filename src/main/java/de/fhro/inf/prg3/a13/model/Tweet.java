package de.fhro.inf.prg3.a13.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import twitter4j.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Kurfer
 * Created on 1/11/18.
 */
public class Tweet {

    private static final Pattern clientPattern = Pattern.compile("^\\<[A-z =\":\\/\\.#!]+\\>([A-z ]+)\\<\\/a\\>$");

    private long id;
    private String text;
    private String source;
    private String language;
    private int retweetCount;

    public Tweet() {
    }

    public Tweet(long id, String text, String source, String language, int retweetCount) {
        this.id = id;
        this.text = text;
        this.source = source;
        this.language = language;
        this.retweetCount = retweetCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Tweet)) return false;

        Tweet tweet = (Tweet) o;

        return new EqualsBuilder()
                .append(getId(), tweet.getId())
                .append(getRetweetCount(), tweet.getRetweetCount())
                .append(getText(), tweet.getText())
                .append(getSource(), tweet.getSource())
                .append(getLanguage(), tweet.getLanguage())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getText())
                .append(getSource())
                .append(getLanguage())
                .append(getRetweetCount())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("text", text)
                .append("source", source)
                .append("language", language)
                .append("retweetCount", retweetCount)
                .toString();
    }

    public String getSourceApp() {
        if(source == null || source.length() == 0) return "Unknown";
        Matcher m = clientPattern.matcher(source);
        if(m.matches() && m.groupCount() >= 1) {
            return m.group(1);
        }
        return "Unknown";
    }

    public static Tweet fromStatus(Status status) {
        return new Tweet(status.getId(), status.getText(), status.getSource(), status.getLang(), status.getRetweetCount());
    }
}

package de.thro.inf.prg3.a13.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Util class to load resources
 *
 * @author Peter Kurfer
 */
public abstract class ResourceUtils {

    private static final String STOP_WORDS_FILE_PATH = "/stopwords.txt";

    private ResourceUtils() {

    }

    /**
     * Load stop words from the app resources
     *
     * @return List of stop words - will be empty if resource file is not found or an error occurred
     */
    public static List<String> loadStopWords() {
        final List<String> stopWords = new LinkedList<>();
        try (final Scanner scanner = new Scanner(ResourceUtils.class.getResourceAsStream(STOP_WORDS_FILE_PATH))) {
            while (scanner.hasNext()) {
                stopWords.add(scanner.next());
            }
        } catch (Exception e) {
            /* ignored */
        }
        return stopWords;
    }
}

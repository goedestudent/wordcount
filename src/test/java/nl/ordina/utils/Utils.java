package nl.ordina.utils;

import java.util.List;

import nl.ordina.interfaces.WordFrequency;

/**
 * Utilities for the test classes
 */
public class Utils {
    /**
     * Given a WordFrequency, returns a string. This string can be used for comparing lists of frequencies.
     * 
     * @param wordFrequency WordFrequency that should be converted to a string
     * @return String that contains the word and frequency in the form "(word, frequency)"
     */
    public static String stringifyWordCount(WordFrequency wordFrequency) {
        return String.format("{%s,%s)", wordFrequency.getWord(), wordFrequency.getFrequency());
    }

    /**
     * Transforms a list of word frequencies into a list of strings representing those frequencies.
     * 
     * @see stringifyWordCount
     * @param wordFrequencies Frequencies to transform into strings
     * @return List of strings that contain the words and frequencies of the wordFrequencies list
     */
    public static String[] stringifyWordCounts(List<WordFrequency> wordFrequencies) {
        String[] wordFrequencyStrings = new String[wordFrequencies.size()];
        for (int i = 0; i < wordFrequencyStrings.length; i++) {
            wordFrequencyStrings[i] = stringifyWordCount(wordFrequencies.get(i));
        }
        return wordFrequencyStrings;
    }
}

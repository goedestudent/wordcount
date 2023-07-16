package nl.ordina.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import nl.ordina.interfaces.WordFrequency;
import nl.ordina.interfaces.WordFrequencyAnalyzer;

/**
 * Class that analyses word occurance in a text.
 */
public class WordFrequencyAnalyzerImpl implements WordFrequencyAnalyzer{
    public WordFrequencyAnalyzerImpl() {
        
    }

    /**
     * Proxy for arithmatic '+' operation. Used by the hashmap for merging values.
     * 
     * @param first  First integer
     * @param second Second integer
     * @return Second integer added to the first integer
     */
    private static Integer addition(Integer first, Integer second) {
        return first + second;
    }

    /**
     * Calculates the frequencies of all words in a given string of text.
     * A word is represented by the characters A-Z and a-z. All other 
     * characters are seen as word seperators. The string
     * "thE sUN1234in    the+sky" thus consists of five words:
     * the sun in the sky.
     * 
     * @param text Text to calculate frequencies for
     * @return Mapping of a word to a frequency. Keys are *lowercase* words.
     */
    private Map<String, Integer> calculateWordFrequencies(String text) {
        // A word is seen as a token with the characters A-Z and a-z, thus
        // we see every other character as a seperator for those characters.
        // Therefore we split on characters that are not A-Z  or a-z.
        String[] words = text.split("[^A-za-z]");
        HashMap<String, Integer> frequencies = new HashMap<>();

        for (String word : words) {
            frequencies.merge(word.toLowerCase(), 1, WordFrequencyAnalyzerImpl::addition);
        }

        return frequencies;
    }

    /**
     * Calculates how often a given word occurs in the given text
     * 
     * @param text Text to calculate frequency in
     * @param word Word to calculate frequency for
     * @return Numer of occurences of the given word in the given string
     */
    @Override
    public int calculateFrequencyForWord(String text, String word) {
        Map<String, Integer> frequencies = calculateWordFrequencies(text);

        // Add 0. If the word does not exist, it now has a value of zero. If it did exist
        // zero was added, thus the number stays the same. This ensures that value we look
        // up does actually exist.
        frequencies.merge(word.toLowerCase(), 0, WordFrequencyAnalyzerImpl::addition);

        return frequencies.get(word.toLowerCase());
    }

    /**
     * Returns the highest frequency of occurance of any word in the given text.
     * If the given text is empty, will return 0 all possible words occur 0 times.
     * 
     * @param text Text to calculate frequencies for
     * @return Highest frequency of occurance of any word in the given text.
     */
    @Override
    public int calculateHighestFrequency(String text) {
        List<WordFrequency> mostFrequent = calculateMostFrequentNWords(text, 1);
        if (mostFrequent.size() == 0) {
            return 0;
        } else {
            return mostFrequent.get(0).getFrequency();
        }
    }

    /**
     * Returns the n words that occur most frequently. If any two words occur the same amount
     * of times, returns the words in alphabetical order.
     * 
     * If n is greater than the amount of unique words in the given text, it will less than
     * n words.
     * 
     * E.g. For the text "The sun shines over the lake" and n=3 
     * will return {("the", 2),("lake", 1), ("over", 1)}.

     * @param text Text to calculate frequencies for
     * @param n    Amount of WordFrequencies that should be returned
     * @return Highest frequency of occurance of any word in the given text.
     */
    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        Map<String, Integer> frequencies = calculateWordFrequencies(text);
        List<WordFrequency> wordFrequencies = new ArrayList<WordFrequency>();

        // Comparision function for frequencies.
        // Note: This comparison is the other way around, thus if the frequency of a < the frequency of b
        // 1 is returned instead of -1. This is because the list is ordered in decending order instead of
        // ascending order. Therfore this method should not be publically available to avoid confusion.
        Comparator<WordFrequency> compareFrequencies = (WordFrequency a, WordFrequency b) -> {
            // 'a' has lower frequency, thus should be after 'b' in the list
            if (a.getFrequency() < b.getFrequency()) {
                return 1;
            // If both have the same frequency, we compare the words, so that alphabetical order is preserved.
            } else if (a.getFrequency() == b.getFrequency()) {
                // Note: all keys are lowercase, thus the rule that Z > a does not have to be taken in to account.
                return a.getWord().compareTo(b.getWord());
            } else {
                return -1;
            }
        };

        // Takes a key-value pair from the map, and inserts in into the result list. When inserting, the
        // required order is taken into account.
        //
        // By sorting while adding, the total runtime is O(n * lg n), whereas by doing it in seperate runs
        // the runtime would be O (n + n * lg n). While they both are O(n * lg n), doing it in one run
        // will reduce the absolute runtime on very large inputs.
        BiConsumer<String, Integer> frequencyToWordFrequency = (String word, Integer frequency) -> {
            WordFrequencyImpl newWordFrequency = new WordFrequencyImpl(word, frequency);
            int newIndex = Math.abs(Collections.binarySearch(wordFrequencies, newWordFrequency, compareFrequencies)) - 1;
            // Note: ArrayList implements RandomAccess, thus this call has a runtime of O(lg n)
            wordFrequencies.add(newIndex, newWordFrequency);
        };

        // Add frequencies from map to the list
        frequencies.forEach(frequencyToWordFrequency);

        // Return only n of the frequencies
        return wordFrequencies.subList(0, n);
    }
}

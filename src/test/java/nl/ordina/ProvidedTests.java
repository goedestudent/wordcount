package nl.ordina;

import org.junit.jupiter.api.Test;

import nl.ordina.application.WordFrequencyAnalyzerImpl;
import nl.ordina.application.WordFrequencyImpl;
import nl.ordina.interfaces.WordFrequency;
import nl.ordina.interfaces.WordFrequencyAnalyzer;
import static nl.ordina.utils.Utils.stringifyWordCounts;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

/**
 * Tests that were given in the specification document.
 * These test whether the minimal requirements are satisfied.
 */
class ProvidedTests {
    WordFrequencyAnalyzer analyzer = new WordFrequencyAnalyzerImpl();

    /**
     * Test whether the WordFrequencyImpl returns correct values.
     */
    @Test
    void frequencyCorrect() {
        String word = "abcD";
        int frequency = 5;
        WordFrequency wordFrequency = new WordFrequencyImpl(word, frequency);
        assertEquals(word.toLowerCase(), wordFrequency.getWord());
        assertEquals(frequency, wordFrequency.getFrequency());
    }

    /**
     * Test whether the definition is correct for the given example.
     */
    @Test
    void assumptionWord() {
        Integer frequency = analyzer.calculateFrequencyForWord("agdfBh", "agdfBh");
        assertEquals(frequency, 1);
    }

    /**
     * Test whether words are indeed case-insensitive.
     */
    @Test
    void assumptionLetterCase() {
        Integer frequency = analyzer.calculateFrequencyForWord("The sun shines over the lake", "The");
        assertEquals(frequency, 2);

        frequency = analyzer.calculateFrequencyForWord("The sun shines over the lake", "the");
        assertEquals(frequency, 2);

        frequency = analyzer.calculateFrequencyForWord("The sun shines over the lake", "tHe");
        assertEquals(frequency, 2);

        frequency = analyzer.calculateFrequencyForWord("The sun shines over tHE lake", "the");
        assertEquals(frequency, 2);

        frequency = analyzer.calculateFrequencyForWord("thE sun shines over The lake", "the");
        assertEquals(frequency, 2);

    }

    /**
     * Test various word-seperating characters.
     */
    @Test
    void assumptionInputText() {
        Integer frequency = analyzer.calculateFrequencyForWord("The input text, consisting of a132 multitude of characters, contains words. They may be seperated by: various separator characters.", "The");
        assertEquals(1, frequency);
    }

    /**
     * Test whether n most frequent words are correctly returned.
     */
    @Test
    void CalculateMostFrequentNWords() {
        String[] frequencies = stringifyWordCounts(analyzer.calculateMostFrequentNWords("The sun shines over the lake", 3));
        String[] expected = stringifyWordCounts(Arrays.asList(new WordFrequencyImpl("the", 2), new WordFrequencyImpl("lake", 1), new WordFrequencyImpl("over", 1)));


        assertArrayEquals(expected, frequencies);
    }
}

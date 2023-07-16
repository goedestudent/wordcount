package nl.ordina;

import nl.ordina.application.WordFrequencyAnalyzerImpl;
import nl.ordina.interfaces.WordFrequency;
import nl.ordina.interfaces.WordFrequencyAnalyzer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

/**
 * Tests to see whether the WordFrequencyAnalyzerImplementation behaves as expected.
 */
class AppTest {
    WordFrequencyAnalyzer analyzer = new WordFrequencyAnalyzerImpl();

    /**
     * Test if word that does not occur in text will not crash the interface.
     */
    @Test
    void doesNotOccur() {
        int frequency = analyzer.calculateFrequencyForWord("Not all words occur in this text", "truck");
        assertEquals(0, frequency);
    }

    /**
     * Test if an empty text will not crash the interface.
     */
    @Test
    void emptyString() {
        int frequency = analyzer.calculateFrequencyForWord("", "truck");
        assertEquals(0, frequency);
    }

    /**
     * Test if an empty word will not crash the interface.
     */
    @Test
    void emptyWord() {
        int frequency = analyzer.calculateFrequencyForWord("The sun shines over the lake", "");
        assertEquals(0, frequency);
    }

    /**
     * Test if an empty text and empty word will not crash the interface.
     */
    @Test
    void emptyWordAndString() {
        int frequency = analyzer.calculateFrequencyForWord("", "");
        assertEquals(0, frequency);
    }

    /**
     * Test if the highest frequency is zero if the text is empty.
     */
    @Test
    void highestFrequencyNoWords() {
        // There are no words, so the highest frequency should be zero.
        int frequency = analyzer.calculateHighestFrequency("");
        assertEquals(0, frequency);
    }

    /**
     * Test if the highest frequency is correct if one word has the most occurences.
     */
    @Test
    void highestFrequencyOneWord() {
        // There are no words, so the highest frequency should be zero.
        int frequency = analyzer.calculateHighestFrequency("This text contains the word 'the' five times, so that the frequency of occurance of the word 'the' should be 5.");
        assertEquals(5, frequency);
    }

    /**
     * Test if the highest frequency is correct if multiple words have the most occurences.
     */
    @Test
    void highestFrequencyMultipleWords() {
        // There are no words, so the highest frequency should be zero.
        int frequency = analyzer.calculateHighestFrequency("The the the, cat cat cat, is is is, cute cute cute");
        assertEquals(3, frequency);
    }

    /**
     * Test if the amount of returned words is correct for i between 0 and 100.
     */
    @Test
    void calculateNMostFrequentNWords() {
        // Check whether the amount of returned words is always less than or equal to the requested amount of words.
        // Less then, because N might be greather than the amount of words in the text or the highest frequency of any word.
        for (int i = 0; i < 100; i++) {
            List<WordFrequency> frequencies = analyzer.calculateMostFrequentNWords("The sun shines over the lake", i);
            boolean sizeCorrect = frequencies.size() <= i;
            assertEquals(true, sizeCorrect);
        }
    }

    /**
     * Test if the amount of returned words is 0 if the text is empty
     */
    @Test
    void calculateNMostFrequentNWordsForEmptyText() {
        for (int i = 0; i < 100; i++) {
            List<WordFrequency> frequencies = analyzer.calculateMostFrequentNWords("", i);
            boolean sizeCorrect = frequencies.size() == 0;
            assertEquals(true, sizeCorrect);
        }
    }
}

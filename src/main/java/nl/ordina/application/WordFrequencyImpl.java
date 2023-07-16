package nl.ordina.application;

import nl.ordina.interfaces.WordFrequency;

/**
 * Class that stores the frequency and string representation of a word.
 */
public class WordFrequencyImpl implements WordFrequency{
    private String word;
    private int frequency;

    /**
     * Implementation of the 'WordFrequency' interface.
     * 
     * Stores the frequency and string representation of a word in a larger text.
     * 
     * @param word       The word that this associated with this object.
     * @param frequency  The frequency of occurance of the associated word.
     */
    public WordFrequencyImpl(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    /**
     * Returns the word that this object is associated with.
     */
    @Override
    public String getWord() {
        return word;
    }

    /**
     * Returns the frequency of occurance of the word that this object is 
     * associated with.
     */
    @Override
    public int getFrequency() {
        return frequency;
    }
}

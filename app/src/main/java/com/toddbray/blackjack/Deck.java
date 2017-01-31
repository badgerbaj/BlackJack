package com.toddbray.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Brad on 1/28/2017.
 *
 */

public class Deck extends MainActivity {

    private HashMap<Integer, Integer> deck;
    private int[] shuffleOrder;
    private int deckPosition;
    private int deckCount;

    public HashMap<Integer, Integer> getDeck() {
        return deck;
    }

    public int[] getShuffleOrder() {
        return shuffleOrder;
    }

    public int getDeckPosition() {
        return deckPosition;
    }

    public int getDeckCount() {
        return deckCount;
    }

    public void setDeck(HashMap<Integer, Integer> deck) {
        this.deck = deck;
    }

    public void setShuffleOrder(int[] shuffleOrder) {
        this.shuffleOrder = shuffleOrder;
    }

    public void setDeckPosition(int deckPosition) {
        this.deckPosition = deckPosition;
    }

    public void incrementDeckPosition() { this.deckPosition++; }

    public void setDeckCount(int deckCount) {
        this.deckCount = deckCount;
    }

    public Deck() {
        this.deck = new HashMap<>();
        this.deck.put(1, R.drawable.ace_clubs);
        this.deck.put(2, R.drawable.two_clubs);
        this.deck.put(3, R.drawable.three_clubs);
        this.deck.put(4, R.drawable.four_clubs);
        this.deck.put(5, R.drawable.five_clubs);
        this.deck.put(6, R.drawable.six_clubs);
        this.deck.put(7, R.drawable.seven_clubs);
        this.deck.put(8, R.drawable.eight_clubs);
        this.deck.put(9, R.drawable.nine_clubs);
        this.deck.put(10, R.drawable.ten_clubs);
        this.deck.put(11, R.drawable.jack_clubs);
        this.deck.put(12, R.drawable.queen_clubs);
        this.deck.put(13, R.drawable.king_clubs);
        this.deck.put(14, R.drawable.ace_spades);
        this.deck.put(15, R.drawable.two_spades);
        this.deck.put(16, R.drawable.three_spades);
        this.deck.put(17, R.drawable.four_spades);
        this.deck.put(18, R.drawable.five_spades);
        this.deck.put(19, R.drawable.six_spades);
        this.deck.put(20, R.drawable.seven_spades);
        this.deck.put(21, R.drawable.eight_spades);
        this.deck.put(22, R.drawable.nine_spades);
        this.deck.put(23, R.drawable.ten_spades);
        this.deck.put(24, R.drawable.jack_spades);
        this.deck.put(25, R.drawable.queen_spades);
        this.deck.put(26, R.drawable.king_spades);
        this.deck.put(27, R.drawable.ace_hearts);
        this.deck.put(28, R.drawable.two_hearts);
        this.deck.put(29, R.drawable.three_hearts);
        this.deck.put(30, R.drawable.four_hearts);
        this.deck.put(31, R.drawable.five_hearts);
        this.deck.put(32, R.drawable.six_hearts);
        this.deck.put(33, R.drawable.seven_hearts);
        this.deck.put(34, R.drawable.eight_hearts);
        this.deck.put(35, R.drawable.nine_hearts);
        this.deck.put(36, R.drawable.ten_hearts);
        this.deck.put(37, R.drawable.jack_hearts);
        this.deck.put(38, R.drawable.queen_hearts);
        this.deck.put(39, R.drawable.king_hearts);
        this.deck.put(40, R.drawable.ace_diamonds);
        this.deck.put(41, R.drawable.two_diamonds);
        this.deck.put(42, R.drawable.three_diamonds);
        this.deck.put(43, R.drawable.four_diamonds);
        this.deck.put(44, R.drawable.five_diamonds);
        this.deck.put(45, R.drawable.six_diamonds);
        this.deck.put(46, R.drawable.seven_diamonds);
        this.deck.put(47, R.drawable.eight_diamonds);
        this.deck.put(48, R.drawable.nine_diamonds);
        this.deck.put(49, R.drawable.ten_diamonds);
        this.deck.put(50, R.drawable.jack_diamonds);
        this.deck.put(51, R.drawable.queen_diamonds);
        this.deck.put(52, R.drawable.king_diamonds);

    }

    public void shuffle(int deckCount){
        this.deckPosition = 0;
        this.deckCount = deckCount;
        this.shuffleOrder = new int[(CARD_COUNT * deckCount)];

        int[] usageLimit = new int[CARD_COUNT];
        Random rn = new Random();
        int newNum;

        for(int i = 0; i < (CARD_COUNT*deckCount); i++){
            newNum = rn.nextInt(CARD_COUNT) + 1;
            while (usageLimit[(newNum-1)] > deckCount) {
                newNum = rn.nextInt(CARD_COUNT) + 1;
            }
            usageLimit[(newNum-1)]++;
            shuffleOrder[i] = newNum;
        }
    }

    public int getCardValue(int card) {
        int value = translateBaseThirteen(card);

        if (value == 1) {
            // Ace
            return 11;
        }
        else if (value > 9) {
            // Face Card
            return 10;
        }
        else return value;
    }

    public int translateBaseThirteen(int card) {
        int value = 0;
        int subtract = 0;
        int[] intArray = new int[CARD_COUNT];
        for(int i=1; i <= CARD_COUNT; i++) { intArray[(i-1)] = i; }

        for( int i : intArray ) {
            if ((i % 13) == 0) {
                if (i == card) {
                    return (i - subtract);
                }
                subtract += 13;
            } else {
                if (i == card) {
                    return (i - subtract);
                }
            }
        }
        return value;
    }
}

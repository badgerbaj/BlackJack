package com.toddbray.blackjack;

import java.util.List;

/**
 * Created by Brad on 1/28/2017.
 *
 */

public class GameState extends MainActivity {

    private int playerScore;
    private int dealerScore;
    private int playerCash;
    private int betAmount;
    private int[] dealerHand;
    private int[] playerHand;

    // Getters
    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public int getPlayerCash() {
        return playerCash;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public int[] getDealerHand() { return dealerHand; }

    public int[] getPlayerHand() { return playerHand; }


    // Setters
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setDealerScore(int dealerScore) {
        this.dealerScore = dealerScore;
    }

    public void setPlayerCash(int playerCash) {
        this.playerCash = playerCash;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public void setDealerHand(int[] dealerHand) { this.dealerHand = dealerHand; }

    public void setPlayerHand(int[] playerHand) { this.dealerHand = playerHand; }

    public void setPlayerHandPos(int pos, int value) { this.playerHand[pos] = value; }

    public void setDealerHandPos(int pos, int value) { this.dealerHand[pos] = value; }

    // Constructor
    public GameState() {
        dealerHand = new int[MAX_HAND];
        playerHand = new int[MAX_HAND];
        startNew();
    }

    // Helpers
    public void startNew() {
        playerScore = 0;
        dealerScore = 0;
        playerCash = 20;
        betAmount = 0;
        for(int i = 0; i < MAX_HAND; i++) {
            dealerHand[i] = 0;
            playerHand[i] = 0;
        }
    }

}

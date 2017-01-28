package com.toddbray.blackjack;

/**
 * Created by Brad on 1/28/2017.
 *
 */

public class Gamestate extends MainActivity {

    private int playerScore;
    private int dealerScore;
    private int playerCash;
    private int betAmount;

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

    public Gamestate() {
        startNew();
    }

    public void startNew() {
        playerScore = 0;
        dealerScore = 0;
        playerCash = 20;
        betAmount = 0;
    }

}

package com.toddbray.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Brad on 1/28/2017.
 *
 */

public class activity_game extends MainActivity implements View.OnClickListener {

    private HashMap<Integer, Integer> playerCards;
    private HashMap<Integer, Integer> dealerCards;

    private TextView tv_PlayerScore;
    private TextView tv_DealerScore;
    private TextView tv_Cash;
    private int downcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            playGame.setPlayerCash(extras.getInt(PLAYER_CASH_KEY));
            playGame.setBetAmount(extras.getInt(BET_AMOUNT_KEY));
        }

        int[] imageViewDealerIds = {R.id.dealer_1_imageView, R.id.dealer_2_imageView, R.id.dealer_3_imageView,
            R.id.dealer_4_imageView, R.id.dealer_5_imageView, R.id.dealer_6_imageView, R.id.dealer_7_imageView,
            R.id.dealer_8_imageView};
        dealerCards = new HashMap<Integer, Integer>();
        for(int i=0; i < MAX_HAND; i++) {
            dealerCards.put(i, imageViewDealerIds[i]);
        }

        int[] imageViewPlayerIds = {R.id.player_1_imageView, R.id.player_2_imageView, R.id.player_3_imageView,
                R.id.player_4_imageView, R.id.player_5_imageView, R.id.player_6_imageView, R.id.player_7_imageView,
                R.id.player_8_imageView};
        playerCards = new HashMap<Integer, Integer>();
        for(int i=0; i < MAX_HAND; i++) {
            playerCards.put(i, imageViewPlayerIds[i]);
        }

        tv_Cash = (TextView) findViewById(R.id.cash_textView);
        tv_Cash.setText(String.valueOf(playGame.getPlayerCash()));

        tv_DealerScore = (TextView) findViewById(R.id.dealer_score_textView);
        tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));

        tv_PlayerScore = (TextView) findViewById(R.id.player_score_textView);
        tv_PlayerScore.setText(String.valueOf(playGame.getPlayerScore()));

        int[] buttonIds = {R.id.hit_button, R.id.stand_button };
        for( int id : buttonIds)
        {
            Button b = (Button) findViewById(id);
            b.setOnClickListener(this);
        }

        playDeck.shuffle(1);

        int i = 0;
        // FIRST PLAYER CARD
        // Draw the next card in the shuffled deck
        ImageView iv = (ImageView) findViewById(playerCards.get(i));
        iv.setImageResource(playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]));

        // Add drawn card to hand
        playGame.setPlayerHandPos(i, playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);

        // Calculate Score
        int tally = playGame.getPlayerScore();
        tally += playDeck.getCardValue(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
        playGame.setPlayerScore(tally);

        // Display New Score
        tv_PlayerScore.setText(String.valueOf(playGame.getPlayerScore()));

        // Move to the next card in the shuffled deck
        playDeck.incrementDeckPosition();

        // FIRST DEALER CARD
        // Draw the next card in the shuffled deck
        iv = (ImageView) findViewById(dealerCards.get(i));
        iv.setImageResource(playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]));

        // Add drawn card to hand
        playGame.setDealerHandPos(i, playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);

        // Calculate Score
        int dealer_tally = playGame.getDealerScore();
        dealer_tally += playDeck.getCardValue(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
        playGame.setDealerScore(dealer_tally);

        // Display New Score
        tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));

        // Move to the next card in the shuffled deck
        playDeck.incrementDeckPosition();

        i++;

        // SECOND PLAYER CARD
        // Draw the next card in the shuffled deck
        iv = (ImageView) findViewById(playerCards.get(i));
        iv.setImageResource(playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]));

        // Add drawn card to hand
        playGame.setPlayerHandPos(i, playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);

        // Calculate Score
        tally = playGame.getPlayerScore();
        tally += playDeck.getCardValue(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
        playGame.setPlayerScore(tally);

        // Display New Score
        tv_PlayerScore.setText(String.valueOf(playGame.getPlayerScore()));

        // Move to the next card in the shuffled deck
        playDeck.incrementDeckPosition();

        // SECOND DEALER CARD
        // Draw the next card in the shuffled deck
        iv = (ImageView) findViewById(dealerCards.get(i));
        iv.setImageResource(R.drawable.card_back);
        downcard = playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);

        // Add drawn card to hand
        playGame.setDealerHandPos(i, playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);

        // Calculate Score
        dealer_tally = playGame.getDealerScore();
        dealer_tally += playDeck.getCardValue(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
        playGame.setDealerScore(dealer_tally);

        // Move to the next card in the shuffled deck
        playDeck.incrementDeckPosition();

    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this, "Player Cash: " + playGame.getPlayerCash(), Toast.LENGTH_LONG).show();
        switch(v.getId()) {
            case R.id.hit_button:

                for(int i = 0; i < MAX_HAND; i++) {
                    if(playGame.getPlayerHand()[i] == 0) {

                        // Draw the next card in the shuffled deck
                        ImageView iv = (ImageView) findViewById(playerCards.get(i));
                        iv.setImageResource(playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]));

                        // Add drawn card to hand
                        playGame.setPlayerHandPos(i, playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);

                        // Calculate Score
                        int tally = playGame.getPlayerScore();
                        tally += playDeck.getCardValue(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
                        playGame.setPlayerScore(tally);

                        // Display New Score
                        tv_PlayerScore.setText(String.valueOf(playGame.getPlayerScore()));

                        // TODO: Handle bust and BlackJack

                        // Move to the next card in the shuffled deck
                        playDeck.incrementDeckPosition();

                        // Exit Loop Early
                        i = MAX_HAND;
                    }
                }

                break;
            case R.id.stand_button:

                ImageView iv = (ImageView) findViewById(dealerCards.get(1));
                iv.setImageResource(downcard);

                // Display New Score
                tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));

                while(playGame.getDealerScore() < 17) {

                    for(int i = 0; i < MAX_HAND; i++) {
                        if(playGame.getDealerHand()[i] == 0) {

                            // Draw the next card in the shuffled deck
                            iv = (ImageView) findViewById(dealerCards.get(i));
                            iv.setImageResource(playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]));

                            // Add drawn card to hand
                            playGame.setDealerHandPos(i, playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);

                            // Calculate Score
                            int dealer_tally = playGame.getDealerScore();
                            dealer_tally += playDeck.getCardValue(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
                            playGame.setDealerScore(dealer_tally);

                            // Display New Score
                            tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));

                            // TODO: Handle bust and BlackJack

                            // Move to the next card in the shuffled deck
                            playDeck.incrementDeckPosition();

                            // Exit Loop Early
                            i = MAX_HAND;
                        }
                    }

                }

                break;
        }
    }
}

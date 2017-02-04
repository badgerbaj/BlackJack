package com.toddbray.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import java.io.File;
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
            invokeXML.writeGameXML(playGame, (new File(this.getFilesDir(), FILE_NAME_GAME)));
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
        tv_DealerScore = (TextView) findViewById(R.id.dealer_score_textView);
        tv_PlayerScore = (TextView) findViewById(R.id.player_score_textView);

        int[] buttonIds = {R.id.hit_button, R.id.stand_button, R.id.next_button };
        for( int id : buttonIds)
        {
            Button b = (Button) findViewById(id);
            b.setOnClickListener(this);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        invokeXML.writeGameXML(playGame, (new File(this.getFilesDir(), FILE_NAME_GAME)));
        invokeXML.writeDeckXML(playDeck, (new File(this.getFilesDir(), FILE_NAME_DECK)));
    }
    @Override
    protected void onResume(){
        super.onResume();

        playGame = invokeXML.readGameXML(new File(this.getFilesDir(), FILE_NAME_GAME));
        playDeck = invokeXML.readDeckXML(new File(this.getFilesDir(), FILE_NAME_DECK));

        if(playGame.getPlayerHand()[0] == 0) {

            // For Testing
            playDeck.shuffle(1);

            // First Player Card
            DealCard(PLAYER_HAND_KEY, false);

            // First Dealer Card
            DealCard(DEALER_HAND_KEY, false);

            // Second Player Card
            DealCard(PLAYER_HAND_KEY, false);

            // Second Dealer Card
            DealCard(DEALER_HAND_KEY, true);


        }
        else {
            DealHand();
        }

        TestSoftHand(PLAYER_HAND_KEY);
        TestSoftHand(DEALER_HAND_KEY);

        int tally = playGame.getPlayerScore();
        int dealer_tally = playGame.getDealerScore();
        ImageView iv = (ImageView) findViewById(dealerCards.get(1));

        if (tally == 21 && dealer_tally == 21) {
            iv.setImageResource(downcard);
            tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));
            Button b = (Button) findViewById(R.id.hit_button);
            b.setVisibility(View.INVISIBLE);
            b = (Button) findViewById(R.id.stand_button);
            b.setVisibility(View.INVISIBLE);
            b = (Button) findViewById(R.id.next_button);
            b.setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.result_textView);
            tv.setText("PUSH");
            tv.setVisibility(View.VISIBLE);
            int winnings = playGame.getBetAmount();
            playGame.setPlayerCash(playGame.getPlayerCash() + winnings);
        } else if (tally == 21) {
            Button b = (Button) findViewById(R.id.hit_button);
            b.setVisibility(View.INVISIBLE);
            b = (Button) findViewById(R.id.stand_button);
            b.setVisibility(View.INVISIBLE);
            b = (Button) findViewById(R.id.next_button);
            b.setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.result_textView);
            tv.setText("BLACKJACK!");
            tv.setVisibility(View.VISIBLE);
            int winnings = playGame.getBetAmount() * 2;
            playGame.setPlayerCash(playGame.getPlayerCash() + winnings);
        } else if (dealer_tally == 21) {
            iv.setImageResource(downcard);
            tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));
            Button b = (Button) findViewById(R.id.hit_button);
            b.setVisibility(View.INVISIBLE);
            b = (Button) findViewById(R.id.stand_button);
            b.setVisibility(View.INVISIBLE);
            b = (Button) findViewById(R.id.next_button);
            b.setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.result_textView);
            tv.setText("DEALER HAS BLACKJACK");
            tv.setVisibility(View.VISIBLE);
        }

        tv_Cash.setText(String.valueOf(playGame.getPlayerCash()));
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this, "Player Cash: " + playGame.getPlayerCash(), Toast.LENGTH_LONG).show();
        switch(v.getId()) {
            case R.id.hit_button:

                DealCard(PLAYER_HAND_KEY, false);

                int tally = playGame.getPlayerScore();
                if(tally > 21) {

                    TestSoftHand(PLAYER_HAND_KEY);

                    if (playGame.getPlayerScore() > 21) {
                        Button b = (Button) findViewById(R.id.hit_button);
                        b.setVisibility(View.INVISIBLE);
                        b = (Button) findViewById(R.id.stand_button);
                        b.setVisibility(View.INVISIBLE);
                        b = (Button) findViewById(R.id.next_button);
                        b.setVisibility(View.VISIBLE);
                        TextView tv = (TextView) findViewById(R.id.result_textView);
                        tv.setText("You BUSTED");
                        tv.setVisibility(View.VISIBLE);
                        break;
                    }
                }
                break;
            case R.id.stand_button:

                Button b = (Button) findViewById(R.id.hit_button);
                b.setVisibility(View.INVISIBLE);

                b = (Button) findViewById(R.id.stand_button);
                b.setVisibility(View.INVISIBLE);

                ImageView iv = (ImageView) findViewById(dealerCards.get(1));
                iv.setImageResource(downcard);

                // Display New Score
                tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));

                while(playGame.getDealerScore() < playGame.getPlayerScore() && playGame.getPlayerScore() < 22) {
                    while(playGame.getDealerScore() < 17) {
                        DealCard(DEALER_HAND_KEY, false);
                        if (playGame.getDealerScore() > 21) TestSoftHand(DEALER_HAND_KEY);
                    }
                    if(playGame.getDealerScore() >= 17) break;
                }

                if(playGame.getDealerScore() > 21) {
                    b = (Button) findViewById(R.id.hit_button);
                    b.setVisibility(View.INVISIBLE);
                    b = (Button) findViewById(R.id.stand_button);
                    b.setVisibility(View.INVISIBLE);
                    b = (Button) findViewById(R.id.next_button);
                    b.setVisibility(View.VISIBLE);
                    TextView tv = (TextView) findViewById(R.id.result_textView);
                    tv.setText("Dealer BUSTS, You Win");
                    tv.setVisibility(View.VISIBLE);
                    int winnings = playGame.getBetAmount() * 2;
                    playGame.setPlayerCash(playGame.getPlayerCash() + winnings);
                    break;
                }

                if(playGame.getPlayerScore() > playGame.getDealerScore() && playGame.getPlayerScore() <= 21 && playGame.getDealerScore() <= 21){
                    b = (Button) findViewById(R.id.hit_button);
                    b.setVisibility(View.INVISIBLE);
                    b = (Button) findViewById(R.id.stand_button);
                    b.setVisibility(View.INVISIBLE);
                    b = (Button) findViewById(R.id.next_button);
                    b.setVisibility(View.VISIBLE);
                    TextView tv = (TextView) findViewById(R.id.result_textView);
                    tv.setText("YOU WIN");
                    tv.setVisibility(View.VISIBLE);
                    int winnings = playGame.getBetAmount() * 2;
                    playGame.setPlayerCash(playGame.getPlayerCash() + winnings);
                    break;
                }

                if(playGame.getPlayerScore() < playGame.getDealerScore() && playGame.getPlayerScore() <= 21 && playGame.getDealerScore() <= 21){
                    b = (Button) findViewById(R.id.hit_button);
                    b.setVisibility(View.INVISIBLE);
                    b = (Button) findViewById(R.id.stand_button);
                    b.setVisibility(View.INVISIBLE);
                    b = (Button) findViewById(R.id.next_button);
                    b.setVisibility(View.VISIBLE);
                    TextView tv = (TextView) findViewById(R.id.result_textView);
                    tv.setText("DEALER WINS");
                    tv.setVisibility(View.VISIBLE);
                    break;
                }

                if(playGame.getPlayerScore() == playGame.getDealerScore() && playGame.getPlayerScore() <= 21 && playGame.getDealerScore() <= 21){
                    b = (Button) findViewById(R.id.hit_button);
                    b.setVisibility(View.INVISIBLE);
                    b = (Button) findViewById(R.id.stand_button);
                    b.setVisibility(View.INVISIBLE);
                    b = (Button) findViewById(R.id.next_button);
                    b.setVisibility(View.VISIBLE);
                    TextView tv = (TextView) findViewById(R.id.result_textView);
                    tv.setText("PUSH");
                    tv.setVisibility(View.VISIBLE);
                    int winnings = playGame.getBetAmount();
                    playGame.setPlayerCash(playGame.getPlayerCash() + winnings);
                    break;
                }

            case R.id.next_button:
                Intent iActivity_Bet = new Intent(getApplicationContext(), activity_bet.class);
                iActivity_Bet.putExtra(PLAYER_CASH_KEY, (playGame.getPlayerCash()));
                startActivity(iActivity_Bet);
                break;

        }
    }

    public void DealHand() {
        for(int i =0; i < MAX_HAND; i++) {
            if (playGame.getPlayerHand()[i] != 0) {

                // Draw the next card in the shuffled deck
                ImageView iv = (ImageView) findViewById(playerCards.get(i));
                iv.setImageResource(playDeck.getDeck().get(playGame.getPlayerHand()[i]));

            }
            if (playGame.getDealerHand()[i] != 0) {

                // Draw the next card in the shuffled deck
                ImageView iv = (ImageView) findViewById(dealerCards.get(i));


                if(i>0) {
                    iv.setImageResource(R.drawable.card_back);
                    downcard = playDeck.getDeck().get(playGame.getDealerHand()[i]);
                }
                else iv.setImageResource(playDeck.getDeck().get(playGame.getDealerHand()[i]));
            }
        }
    }

    public void DealCard(String target, boolean isHidden) {
        for(int i = 0; i < MAX_HAND; i++) {
            switch (target) {
                case PLAYER_HAND_KEY:
                    if (playGame.getPlayerHand()[i] == 0) {

                        // Draw the next card in the shuffled deck
                        ImageView iv = (ImageView) findViewById(playerCards.get(i));
                        if(isHidden) {
                            iv.setImageResource(R.drawable.card_back);
                            downcard = playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
                        }
                        iv.setImageResource(playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]));

                        // Add drawn card to hand
                        playGame.setPlayerHandPos(i, playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);

                        //Toast.makeText(this, "Card " + i + " Drawn: " + playDeck.getShuffleOrder()[playDeck.getDeckPosition()], Toast.LENGTH_LONG).show();

                        // Calculate Score
                        int tally = playGame.getPlayerScore();
                        tally += playDeck.getCardValue(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
                        playGame.setPlayerScore(tally);

                        // Display New Score
                        tv_PlayerScore.setText(String.valueOf(playGame.getPlayerScore()));

                        // Move to the next card in the shuffled deck
                        playDeck.incrementDeckPosition();

                        // Exit Loop Early
                        i = MAX_HAND;
                    }
                    break;
                case DEALER_HAND_KEY:
                    if (playGame.getDealerHand()[i] == 0) {

                        // Draw the next card in the shuffled deck
                        ImageView iv = (ImageView) findViewById(dealerCards.get(i));
                        if(isHidden) {
                            iv.setImageResource(R.drawable.card_back);
                            downcard = playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
                        }
                        else iv.setImageResource(playDeck.getDeck().get(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]));

                        // Add drawn card to hand
                        playGame.setDealerHandPos(i, playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);

                        //Toast.makeText(this, "Card " + i + " Drawn: " + playDeck.getShuffleOrder()[playDeck.getDeckPosition()], Toast.LENGTH_LONG).show();

                        // Calculate Score
                        int tally = playGame.getDealerScore();
                        tally += playDeck.getCardValue(playDeck.getShuffleOrder()[playDeck.getDeckPosition()]);
                        playGame.setDealerScore(tally);

                        // Display New Score
                        if(isHidden == false) tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));

                        // Move to the next card in the shuffled deck
                        playDeck.incrementDeckPosition();

                        // Exit Loop Early
                        i = MAX_HAND;
                    }
                    break;
            }
        }
    }

    public void TestSoftHand(String target) {

        int tally;
        int ace;

        switch (target) {
            case PLAYER_HAND_KEY:
                // Find out how many times 10 can be subtracted on bust
                ace = 0;
                for (int ii = 0; ii < MAX_HAND; ii++) {
                    if (playDeck.getCardValue(playGame.getPlayerHand()[ii]) == 11) {
                        ace++;
                    }
                }

                // Recount hand score
                tally = playDeck.getHandScore(playGame.getPlayerHand());
                // Remove 10 if over 21 per ace available
                for (int ii = 0; ii < ace; ii++) {
                    if (tally > 21) {
                        tally -= 10;
                    }
                }

                // Set Score
                playGame.setPlayerScore(tally);

                // Display New Score
                tv_PlayerScore.setText(String.valueOf(playGame.getPlayerScore()));
                break;

            case DEALER_HAND_KEY:
                // Find out how many times 10 can be subtracted on bust
                ace = 0;
                for (int ii = 0; ii < MAX_HAND; ii++) {
                    if (playDeck.getCardValue(playGame.getDealerHand()[ii]) == 11) {
                        ace++;
                    }
                }

                // Recount hand score
                tally = playDeck.getHandScore(playGame.getDealerHand());
                // Remove 10 if over 21 per ace available
                for (int ii = 0; ii < ace; ii++) {
                    if (tally > 21) {
                        tally -= 10;
                    }
                }

                // Set Score
                playGame.setDealerScore(tally);

                // Display New Score
                tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));
                break;
        }
    }
}
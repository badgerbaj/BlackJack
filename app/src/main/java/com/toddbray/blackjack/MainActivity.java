package com.toddbray.blackjack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String SHUFFLE_KEY = "shuffle";
    public static final String DECK_POSITION_KEY = "deckposition";
    public static final String DECK_COUNT_KEY = "deckcount";
    public static final String PLAYER_CASH_KEY = "playercash";
    public static final String PLAYER_SCORE_KEY = "playerscore";
    public static final String PLAYER_HAND_KEY = "playerhand";
    public static final String DEALER_SCORE_KEY = "dealerscore";
    public static final String DEALER_HAND_KEY = "dealerhand";
    public static final String BET_AMOUNT_KEY = "betamount";
    public static final String NUMBER_KEY = "number";
    public static final String FILE_NAME = "play_data.xml";

    public static final int CARD_COUNT = 52;
    public static final int BET_AMOUNT_ONE = 1;
    public static final int BET_AMOUNT_FIVE = 5;
    public static final int BET_AMOUNT_TEN = 10;
    public static final int BET_AMOUNT_TWENTY = 20;
    public static final int MAX_HAND = 8;

    public Deck playDeck;
    public GameState playGame;
    public InvokeXML invokeXML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] buttonIds = {R.id.play_button, R.id.continue_button};
        for( int id : buttonIds)
        {
            Button b = (Button) findViewById(id);
            b.setOnClickListener(this);
        }
        invokeXML = new InvokeXML();
        playDeck = new Deck();
        playGame = new GameState();

        // Needed until saveToXml is broken in to separate deck and game files
        playDeck.shuffle(1);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.play_button:
                // Call Bidding Activity

                invokeXML.saveToXML(playGame, playDeck,  new File(this.getFilesDir(), FILE_NAME));

                Intent iActivity_Bet = new Intent(getApplicationContext(), activity_bet.class);
                iActivity_Bet.putExtra(PLAYER_CASH_KEY, playGame.getPlayerCash());
                startActivity(iActivity_Bet);
                break;
            case R.id.continue_button:
                //File file = new File(this.getFilesDir(), FILE_NAME);
                playGame = invokeXML.readGameXML(new File(this.getFilesDir(), FILE_NAME));
                if(playGame.getPlayerCash() > 0) {
                    Intent iActivity_Game = new Intent(getApplicationContext(), activity_game.class);
                    startActivity(iActivity_Game);
                }
                else {
                    Toast.makeText(this, "No valid gameplay found for resume", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
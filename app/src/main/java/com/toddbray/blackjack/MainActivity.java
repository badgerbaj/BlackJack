package com.toddbray.blackjack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String SHUFFLE_KEY = "shuffle";
    public static final String DECK_POSITION_KEY = "deckposition";
    public static final String DECK_COUNT_KEY = "deckcount";
    public static final String PLAYER_CASH_KEY = "playercash";
    public static final String PLAYER_SCORE_KEY = "playerscore";
    public static final String DEALER_SCORE_KEY = "dealerscore";
    public static final String BET_AMOUNT_KEY = "betamount";
    public static final String NUMBER_KEY = "number";
    public final int CARD_COUNT = 52;

    public Deck playDeck;
    public GameState playGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] buttonIds = {R.id.play_button};
        for( int id : buttonIds)
        {
            Button b = (Button) findViewById(id);
            b.setOnClickListener(this);
        }

        playDeck = new Deck();
        playGame = new GameState();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.play_button:
                // Call About_App
                Intent iActivity_Bet = new Intent(getApplicationContext(), activity_bet.class);
                startActivity(iActivity_Bet);
                break;

        }
    }
}

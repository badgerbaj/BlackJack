package com.toddbray.blackjack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String SHUFFLE_KEY = "shuffle";
    public static final String DECK_POSITION_KEY = "deckposition";
    public final int CARD_COUNT = 52;

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

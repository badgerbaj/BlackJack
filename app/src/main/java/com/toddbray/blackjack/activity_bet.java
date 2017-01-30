package com.toddbray.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Brad on 1/28/2017.
 *
 */

public class activity_bet extends MainActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            playGame.setPlayerCash(extras.getInt(PLAYER_CASH_KEY));
        }

        int[] buttonIds = {R.id.one_dollar_imageButton, R.id.five_dollar_imageButton,
                R.id.ten_dollar_imageButton, R.id.twenty_dollar_imageButton };
        for( int id : buttonIds)
        {
            ImageButton b = (ImageButton) findViewById(id);
            b.setOnClickListener(this);
        }

        TextView tv_Money = (TextView) findViewById(R.id.money_textView);
        tv_Money.setText(String.valueOf(playGame.getPlayerCash()));
    }

    @Override
    public void onClick(View v) {

        //TODO: Validate the player has the required cash
        //TODO: Pass playGame data as intent
        Intent iActivity_Game = new Intent(getApplicationContext(), activity_game.class);

        switch(v.getId()) {
            case R.id.one_dollar_imageButton:
                //
                iActivity_Game.putExtra(PLAYER_CASH_KEY, (playGame.getPlayerCash() - BET_AMOUNT_ONE));
                iActivity_Game.putExtra(BET_AMOUNT_KEY, BET_AMOUNT_ONE);
                //Toast.makeText(this, "Player Cash: " + MainActivity.playGame.getPlayerCash(), Toast.LENGTH_LONG).show();
                break;
            case R.id.five_dollar_imageButton:
                //
                playGame.setPlayerCash(playGame.getPlayerCash() - BET_AMOUNT_FIVE);
                playGame.setBetAmount(BET_AMOUNT_FIVE);
                break;
            case R.id.ten_dollar_imageButton:
                //
                playGame.setPlayerCash(playGame.getPlayerCash() - BET_AMOUNT_TEN);
                playGame.setBetAmount(BET_AMOUNT_TEN);
                break;
            case R.id.twenty_dollar_imageButton:
                //
                playGame.setPlayerCash(playGame.getPlayerCash() - BET_AMOUNT_TWENTY);
                playGame.setBetAmount(BET_AMOUNT_TWENTY);
                break;
        }

        startActivity(iActivity_Game);
    }
}

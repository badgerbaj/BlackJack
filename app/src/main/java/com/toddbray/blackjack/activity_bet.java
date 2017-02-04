package com.toddbray.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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

        if (playGame.getPlayerCash() < 20) {
            ImageButton b = (ImageButton) findViewById(R.id.twenty_dollar_imageButton);
            b.setVisibility(View.INVISIBLE);
        }

        if (playGame.getPlayerCash() < 10) {
            ImageButton b = (ImageButton) findViewById(R.id.ten_dollar_imageButton);
            b.setVisibility(View.INVISIBLE);
        }

        if (playGame.getPlayerCash() < 5) {
            ImageButton b = (ImageButton) findViewById(R.id.five_dollar_imageButton);
            b.setVisibility(View.INVISIBLE);
        }

        if (playGame.getPlayerCash() < 1) {
            ImageButton b = (ImageButton) findViewById(R.id.one_dollar_imageButton);
            b.setVisibility(View.INVISIBLE);
            TextView tv = (TextView) findViewById(R.id.bet_textView);
            tv.setText("GAME OVER");
        }

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

                break;
            case R.id.five_dollar_imageButton:
                //
                iActivity_Game.putExtra(PLAYER_CASH_KEY, (playGame.getPlayerCash() - BET_AMOUNT_FIVE));
                iActivity_Game.putExtra(BET_AMOUNT_KEY, BET_AMOUNT_FIVE);
                break;
            case R.id.ten_dollar_imageButton:
                //
                iActivity_Game.putExtra(PLAYER_CASH_KEY, (playGame.getPlayerCash() - BET_AMOUNT_TEN));
                iActivity_Game.putExtra(BET_AMOUNT_KEY, BET_AMOUNT_TEN);
                break;
            case R.id.twenty_dollar_imageButton:
                //
                iActivity_Game.putExtra(PLAYER_CASH_KEY, (playGame.getPlayerCash() - BET_AMOUNT_TWENTY));
                iActivity_Game.putExtra(BET_AMOUNT_KEY, BET_AMOUNT_TWENTY);
                break;
        }

        startActivity(iActivity_Game);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent iActivity_Main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(iActivity_Main);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
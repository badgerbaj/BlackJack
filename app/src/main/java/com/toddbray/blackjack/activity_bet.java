package com.toddbray.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Brad on 1/28/2017.
 *
 */

public class activity_bet extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        int[] buttonIds = {R.id.one_dollar_imageButton, R.id.five_dollar_imageButton,
                R.id.ten_dollar_imageButton, R.id.twenty_dollar_imageButton };
        for( int id : buttonIds)
        {
            Button b = (Button) findViewById(id);
            b.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent iActivity_Game = new Intent(getApplicationContext(), activity_game.class);

        switch(v.getId()) {
            case R.id.one_dollar_imageButton:
                //

                break;
            case R.id.five_dollar_imageButton:
                //

                break;
            case R.id.ten_dollar_imageButton:
                //


                break;
            case R.id.twenty_dollar_imageButton:
                //

                break;
        }

        startActivity(iActivity_Game);
    }
}

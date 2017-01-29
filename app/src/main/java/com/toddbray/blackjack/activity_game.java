package com.toddbray.blackjack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Brad on 1/28/2017.
 *
 */

public class activity_game extends MainActivity implements View.OnClickListener {
    // TODO accept intent data from activity_bet

    @Override
    public void onClick(View v) {
        Intent iActivity_Bet = new Intent(getApplicationContext(), activity_game.class);




        startActivity(iActivity_Bet);
    }
}

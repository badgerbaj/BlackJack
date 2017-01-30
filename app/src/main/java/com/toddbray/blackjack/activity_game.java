package com.toddbray.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Brad on 1/28/2017.
 *
 */

public class activity_game extends MainActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //TODO: get playGame data as intent

        int[] imageViewDealerIds = {R.id.dealer_1_imageView, R.id.dealer_2_imageView, R.id.dealer_3_imageView,
            R.id.dealer_4_imageView, R.id.dealer_5_imageView, R.id.dealer_6_imageView, R.id.dealer_7_imageView,
            R.id.dealer_8_imageView};
        HashMap<Integer, Integer> dealerCards = new HashMap<Integer, Integer>();
        for(int i=0; i < MAX_HAND; i++) {
            dealerCards.put(playGame.getDealerHand()[i], imageViewDealerIds[i]);
        }

        int[] imageViewPlayerIds = {R.id.player_1_imageView, R.id.player_2_imageView, R.id.player_3_imageView,
                R.id.player_4_imageView, R.id.player_5_imageView, R.id.player_6_imageView, R.id.player_7_imageView,
                R.id.player_8_imageView};
        HashMap<Integer, Integer> playerCards = new HashMap<Integer, Integer>();
        for(int i=0; i < MAX_HAND; i++) {
            playerCards.put(playGame.getPlayerHand()[i], imageViewPlayerIds[i]);
        }

        TextView tv_Cash = (TextView) findViewById(R.id.cash_textView);
        tv_Cash.setText(String.valueOf(playGame.getPlayerCash()));

        TextView tv_DealerScore = (TextView) findViewById(R.id.dealer_score_textView);
        tv_DealerScore.setText(String.valueOf(playGame.getDealerScore()));

        TextView tv_PlayerScore = (TextView) findViewById(R.id.player_score_textView);
        tv_PlayerScore.setText(String.valueOf(playGame.getPlayerScore()));

        int[] buttonIds = {R.id.hit_button, R.id.stand_button };
        for( int id : buttonIds)
        {
            Button b = (Button) findViewById(id);
            b.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Player Cash: " + playGame.getPlayerCash(), Toast.LENGTH_LONG).show();
    }
}

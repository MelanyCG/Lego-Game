package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PlayerDetails extends AppCompatActivity {
    protected static boolean moveWithSensors=false;
    protected static int legoPlayer, legoLives;
    protected static String PlayerName;
    private EditText userNameEditText;
    private ImageView startAgain;
    private final CharSequence blockStartGameWithoutName = "Enter your name for start playing", chosenPlayer = "A player has been chosen";
    private final int[] keys = {R.drawable.lego1, R.drawable.lego2,R.drawable.lego3,
            R.drawable.lego4,R.drawable.lego5,R.drawable.lego6,R.drawable.lego7,R.drawable.lego8};
    private final int[] values = {R.drawable.lego11, R.drawable.lego22,R.drawable.lego33,
            R.drawable.lego44,R.drawable.lego55,R.drawable.lego66,R.drawable.lego77,R.drawable.lego88};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        userNameEditText = (EditText) findViewById(R.id.userName);
        startAgain = (ImageView) findViewById(R.id.start_game_btn);
        startAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });
        cretePlayersViews();
    }

    private void cretePlayersViews() {
        LinearLayout gallery = findViewById(R.id.players);
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int index = 0; index < 8; index++) {
            View view = inflater.inflate(R.layout.item_player, gallery, false);
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setId(index);
            imageView.setImageResource(keys[index]);
            gallery.addView(view);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    legoPlayer = keys[v.getId()];
                    legoLives = values[v.getId()];
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, chosenPlayer, duration);
                    toast.show();
                }
            });
        }
    }

    private void openGameActivity() {
        PlayerName = userNameEditText.getText().toString();
        if (PlayerName.length() > 0) {
            Intent intent = new Intent(this, TheGame.class);
            startActivity(intent);
        }
        else{
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, blockStartGameWithoutName, duration);
            toast.show();
        }
    }
}

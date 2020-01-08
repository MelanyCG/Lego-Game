package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private final String exitMsg = "EXIT", checkExitMsg = "Are you sure you want to exit?", yesMsg = "YES", noMsg = "NO";
    private LinearLayout linearLayout;
    private ImageView setting_bth, star_btn, startAgain;
    private EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findIDs();
        startAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayerDetails();
            }
        });
        star_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopPlayersActivity();
            }
        });
        setting_bth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingActivity();
            }
        });
    }

    private void findIDs() {
        linearLayout = findViewById(R.id.main_layout);
        startAgain = (ImageView) findViewById(R.id.start_game_btn);
        userNameEditText = (EditText) findViewById(R.id.userName);
        star_btn = (ImageView) findViewById(R.id.star_btn);
        setting_bth = findViewById(R.id.settings);
    }

    private void openPlayerDetails() {
        Intent intent = new Intent(this, PlayerDetails.class);
        startActivity(intent);
    }

    private void openSettingActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openTopPlayersActivity() {
        Intent intent = new Intent(this, MarginActivities.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(exitMsg);
        builder.setMessage(checkExitMsg);
        builder.setPositiveButton(yesMsg, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(noMsg, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

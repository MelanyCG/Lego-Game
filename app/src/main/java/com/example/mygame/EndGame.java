package com.example.mygame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Comparator;

public class EndGame extends AppCompatActivity {
    protected static double latitude;
    protected static double longitude;
    private static final int REQUEST_LOCATION = 1;
    private final String cantGetLocation = "Cant Get Your Location" , enableGPS = "Enable GPS", yesMsg = "YES", noMsg = "NO";
    private int getTotalScore;
    private ImageView top10Image;
    private Winner winner;
    private LocationManager locationManager;
    private String latitudeStr, longitudeStr;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        getTotalScore = getIntent().getIntExtra(TheGame.scoreString, 0);
        TextView textScore = findViewById(R.id.game_over);
        textScore.setText("YOUR SCORE: " + getTotalScore);
        ImageView play_again = findViewById(R.id.play_again);
        top10Image = findViewById(R.id.quality);
        top10Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(EndGame.this, MarginActivities.class);
                    startActivity(intent);
            }
        });
        addPermission();
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewGameActivity();
            }
        });
        addToWinnersList();
    }

    private void addPermission() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS();
        }
        else {
            getLocation();
        }
    }

    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(EndGame.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EndGame.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        }
        else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetWork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (LocationGps!=null) {
                latitude = LocationGps.getLatitude();
                longitude = LocationGps.getLongitude();
                latitudeStr = String.valueOf(latitude);
                longitudeStr = String.valueOf(longitude);

            }
            else if (LocationNetWork!=null) {
                double lat=LocationNetWork.getLatitude();
                double longi=LocationNetWork.getLongitude();
                latitudeStr = String.valueOf(lat);
                longitudeStr = String.valueOf(longi);
            }
            else if (LocationPassive !=null) {
                double latitudeDouble = LocationPassive.getLatitude();
                double longitudeDouble = LocationPassive.getLongitude();
                latitudeStr = String.valueOf(latitudeDouble);
                longitudeStr = String.valueOf(longitudeDouble);

            }
            else {
                Toast.makeText(this, cantGetLocation, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(enableGPS).setCancelable(false).setPositiveButton(yesMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton(noMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void openNewGameActivity() {
        Intent intent = new Intent(this, TheGame.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addToWinnersList() {
        winner = new Winner();
        winner.setName(PlayerDetails.PlayerName);
        winner.setScore(getTotalScore);
        winner.setLatitude(latitudeStr);
        winner.setLongitude(longitudeStr);
        Winner.winners.add(winner);
        SaveDataClass shared = new SaveDataClass(getApplicationContext());
        shared.addWinnerToListAndSave(winner, new Comparator<Winner>() {
            @Override
            public int compare(Winner u, Winner v) {
                if (u.getScore() == v.getScore())
                    return 0;
                return u.getScore() < v.getScore() ? 1 : -1;
            }
        });
    }
}

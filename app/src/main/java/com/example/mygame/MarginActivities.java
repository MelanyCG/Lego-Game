package com.example.mygame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

public class MarginActivities extends AppCompatActivity implements TableFragment.FragmentHighScoreListener {
    private TableFragment tableFragment;
    private MappingFragment mappingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margin_activities);

        tableFragment = new TableFragment();
        mappingFragment = new MappingFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.upper_layout, tableFragment);
        fragmentTransaction.add(R.id.lower_layout, mappingFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onGameUserInfoSent(Winner user) {
        mappingFragment.getGameUserInfo(user);
    }

}

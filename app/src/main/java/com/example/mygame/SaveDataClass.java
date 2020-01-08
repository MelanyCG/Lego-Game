package com.example.mygame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import static android.content.Context.MODE_PRIVATE;

public class SaveDataClass{
    private SharedPreferences shared;
    private ArrayList<Winner> winnerArrayList;
    private String editorString = "task_list", sharedPrefStr = "App";

    public SaveDataClass(Context context) {
        shared = context.getSharedPreferences(sharedPrefStr, MODE_PRIVATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addWinnerToListAndSave(Winner winner, Comparator<Winner> comparator) {
        winnerArrayList = loadData();
        if (!winnerArrayList.contains(winner)) {
            winnerArrayList.add(winner);
            winnerArrayList.sort(comparator);
            storeData(winnerArrayList);
        } else {
            int idx = winnerArrayList.indexOf(winner);
            Winner currentUser = winnerArrayList.get(idx);
            if (currentUser.getScore() < winner.getScore()) {
                winnerArrayList.remove(currentUser);
                winnerArrayList.add(winner);
                winnerArrayList.sort(comparator);
                storeData(winnerArrayList);
            }
        }
    }

    private void storeData(ArrayList<Winner> list) {
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(editorString, json);
        editor.apply();
    }

    public ArrayList<Winner> loadData() {
        Gson gson = new Gson();
        String json = shared.getString(editorString, null);
        Type type = new TypeToken<ArrayList<Winner>>() {}.getType();
        if((winnerArrayList = gson.fromJson(json, type)) == null)
            winnerArrayList = new ArrayList<>();
        return winnerArrayList;
    }
}

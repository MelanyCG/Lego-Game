package com.example.mygame;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import static com.example.mygame.Winner.winners;

public class TableFragment extends Fragment {
    private View rootView;
    private FragmentHighScoreListener listener;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public interface FragmentHighScoreListener {
        void onGameUserInfoSent(Winner user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.table_new, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);
        SaveDataClass shared = new SaveDataClass(getContext());
        winners = shared.loadData();

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
           if(winners.size() <= 10 ) {
                adapter = new RecyclerAdapter(winners);
            }
            if(winners.size() >= 10) {
                adapter = new RecyclerAdapter(new ArrayList<Winner>(winners.subList(0, 10)));
            }
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Winner gameUser = winners.get(position);
                listener.onGameUserInfoSent(gameUser);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentHighScoreListener) {
            listener = (FragmentHighScoreListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
    }
}
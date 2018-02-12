package com.fugenapp.ui.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.fugenapp.R;
import com.fugenapp.adapters.EventRecyclerAdapter;
import com.fugenapp.database.FugenAppDatabase;
import com.fugenapp.database.model.Event;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private View convertView;
    private RecyclerView recyclerView;
    private LottieAnimationView lottieAnimationView;

    private ArrayList<Event> events;
    private EventRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_search, container, false);

        new SearchFragment.LoadData().execute();
        return convertView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = convertView.findViewById(R.id.recycler_view);
        lottieAnimationView = convertView.findViewById(R.id.anim_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void displayData() {
        adapter = new EventRecyclerAdapter(getActivity(), events);
        recyclerView.setAdapter(adapter);
    }

    public void filter(String name) {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (Event e : events) {
            if (e.name.toLowerCase().contains(name.toLowerCase()) || e.cat.toLowerCase().contains(name.toLowerCase()) || e.venue.toLowerCase().contains(name.toLowerCase())) {
                filteredEvents.add(e);
            }
        }
        if (filteredEvents.isEmpty()) {
            recyclerView.setVisibility(View.INVISIBLE);
            lottieAnimationView.setVisibility(View.VISIBLE);
        } else if (name.equalsIgnoreCase("")) {
            recyclerView.setVisibility(View.INVISIBLE);
            lottieAnimationView.setVisibility(View.INVISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
            lottieAnimationView.setVisibility(View.INVISIBLE);
        }
        adapter.filterList(filteredEvents);
    }

    public boolean canFilter(String name) {
        for (Event e : events) {
            if (e.name.toLowerCase().contains(name.toLowerCase()) || e.cat.toLowerCase().contains(name.toLowerCase()) || e.venue.toLowerCase().contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            events = new FugenAppDatabase(getActivity()).getAllEvents();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            displayData();
        }
    }
}

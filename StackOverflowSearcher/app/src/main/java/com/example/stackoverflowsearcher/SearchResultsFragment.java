package com.example.stackoverflowsearcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stackoverflowsearcher.adapter.SearchResultsAdapter;
import com.example.stackoverflowsearcher.models.Discussion;
import com.example.stackoverflowsearcher.models.StackoverflowResult;
import com.example.stackoverflowsearcher.network.IStackOverflowSearcher;
import com.example.stackoverflowsearcher.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsFragment extends Fragment {
    private final static String SEARCH_TEXT_ARG_KEY = "SearchTextArg";

    private IStackOverflowSearcher stackOverflowSearcher;
    private List<Discussion> discussions;

    private RecyclerView searchResultsView;

    public SearchResultsFragment(){}

    public static SearchResultsFragment newInstace(String searchText) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_TEXT_ARG_KEY, searchText);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_second, container, false);
        this.searchResultsView = rootView.findViewById(R.id.searchResults);
        String searchText= getArguments().getString(SEARCH_TEXT_ARG_KEY, "");

        this.stackOverflowSearcher =
                RetrofitInstance.getRetrofitInstance().create(IStackOverflowSearcher.class);
        this.stackOverflowSearcher.getDiscussionsBasedOnSearch(searchText,
                "desc",
                "creation",
                "stackoverflow").enqueue(new Callback<StackoverflowResult>() {
            @Override
            public void onResponse(Call<StackoverflowResult> call, Response<StackoverflowResult> response) {
                discussions = response.body().getItems();

                if(discussions.size() == 0) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.no_results) + ": " + searchText,
                            Toast.LENGTH_LONG).show();
                }

                for (Discussion discussion: discussions) {
                    Log.i("Discussions", discussion.getTitle());
                }

                SearchResultsAdapter adapter = new SearchResultsAdapter(discussions);
                searchResultsView.setAdapter(adapter);
                searchResultsView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            }

            @Override
            public void onFailure(Call<StackoverflowResult> call, Throwable t) {
                Log.e("Searcher", "Error getting results");
            }
        });

        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences preferences =
                this.getContext().getSharedPreferences(MainActivity.SETTING_PREFRENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MainActivity.LAST_SCREEN_PREFRENCE_KEY, this.getClass().getName());
        editor.commit();
    }
}
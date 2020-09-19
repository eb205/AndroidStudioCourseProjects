package com.example.stackoverflowsearcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SearchBarFragment extends Fragment {


    private OnSearchFragmentClick searchListener;

    private EditText searchText;

    public SearchBarFragment() {}

    public static SearchBarFragment newInstance() {
        return new SearchBarFragment();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        this.searchText = rootView.findViewById(R.id.SearchBar);
        SharedPreferences preferences =
                this.getContext().getSharedPreferences(MainActivity.SETTING_PREFRENCE_FILE, Context.MODE_PRIVATE);
        searchText.setText(preferences.getString(MainActivity.LAST_SEARCH_PREFRENCE_KEY, ""));

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnSearchFragmentClick) {
            this.searchListener = (OnSearchFragmentClick) context;
        } else {
            throw new ClassCastException(context.toString() + "Err");
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.SearchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch(view);
            }
        });
    }

    public void onSearch(View view) {
        this.searchListener.onSearchButtonClick(this.searchText.getText().toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences preferences =
                this.getContext().getSharedPreferences(MainActivity.SETTING_PREFRENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MainActivity.LAST_SCREEN_PREFRENCE_KEY, this.getClass().getName());
        editor.putString(MainActivity.LAST_SEARCH_PREFRENCE_KEY, this.searchText.getText().toString());
        editor.commit();

    }


}
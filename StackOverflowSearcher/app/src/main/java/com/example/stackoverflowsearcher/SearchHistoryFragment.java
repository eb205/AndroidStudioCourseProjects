package com.example.stackoverflowsearcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stackoverflowsearcher.adapter.SearchHistoryAdapter;
import com.example.stackoverflowsearcher.models.SearchHistoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchHistoryFragment extends Fragment implements IOnHistoryDelete {
    private SearcherDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase readDatebase;
    private RecyclerView historyView;
    private List<SearchHistoryItem> items;

    public SearchHistoryFragment() {
        // Required empty public constructor
    }


    public static SearchHistoryFragment newInstance() {
        SearchHistoryFragment fragment = new SearchHistoryFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_history, container, false);
        this.historyView = rootView.findViewById(R.id.history_results);

        String[] projections = {
            DatabaseContract.PastSearchesTable.SEARCH_ID_COLUMN_NAME,
                DatabaseContract.PastSearchesTable.SEARCH_TEXT_COLUMN_NAME,
                DatabaseContract.PastSearchesTable.SEARCH_LOCATION_COLUMN_NAME
        };

        Cursor resultCursor = this.readDatebase.query(DatabaseContract.PastSearchesTable.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null);
        resultCursor.moveToFirst();
        this.items = new ArrayList<SearchHistoryItem>();

        for(int resultIndex = 0; resultIndex < resultCursor.getCount(); resultIndex++) {
            items.add(new SearchHistoryItem(resultCursor.getInt(0),
                    resultCursor.getString(1),
                    resultCursor.getString(2)));
            resultCursor.moveToNext();
        }

        SearchHistoryAdapter adapter = new SearchHistoryAdapter(items, this);
        this.historyView.setAdapter(adapter);
        this.historyView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.myDatabaseHelper = new SearcherDatabaseHelper(context);
        this.readDatebase = this.myDatabaseHelper.getReadableDatabase();
    }

    @Override
    public void OnHistoryDeletePress(int historyId) {
        this.readDatebase.delete(DatabaseContract.PastSearchesTable.TABLE_NAME,
                DatabaseContract.PastSearchesTable.SEARCH_ID_COLUMN_NAME + " LIKE ?",
                new String[]{Integer.toString(historyId)});
        int indexToDelete = -1;

        for(int index = 0; index < this.items.size(); index++) {
            if(this.items.get(index).getId() == historyId) {
                indexToDelete = index;
                break;
            }
        }

        if(indexToDelete != -1) {
            this.items.remove(indexToDelete);
        }

        SearchHistoryAdapter adapter = new SearchHistoryAdapter(this.items, this);
        this.historyView.setAdapter(adapter);
        this.historyView.setLayoutManager(new LinearLayoutManager(getContext()));
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
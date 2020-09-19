package com.example.stackoverflowsearcher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stackoverflowsearcher.IOnHistoryDelete;
import com.example.stackoverflowsearcher.R;
import com.example.stackoverflowsearcher.models.SearchHistoryItem;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHold> {
    private List<SearchHistoryItem> items;
    private IOnHistoryDelete onHistoryDelete;

    public SearchHistoryAdapter(List<SearchHistoryItem> items, IOnHistoryDelete onHistoryDelete) {
        this.onHistoryDelete = onHistoryDelete;
        this.items = items;
    }

    @NonNull
    @Override
    public SearchHistoryViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating a layout from XML and returning the holder

        //get inflater
        //the parent is actually the RecyclerView

        Context context = parent.getContext();


        LayoutInflater inflater = LayoutInflater.from(context);

        //by using inflater.inflate() you create your View from your XML file.
        View view = inflater.inflate(R.layout.search_history_card_view, parent, false);

        // Return a new holder instance
        SearchHistoryViewHold viewHolder = new SearchHistoryViewHold(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryViewHold holder, int position) {
        SearchHistoryItem currItem = this.items.get(position);
        holder.searchText.setText(currItem.getId() + ": " + currItem.getSearchText());
        holder.deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHistoryDelete.OnHistoryDeletePress(currItem.getId());
            }
        });

        holder.historyLocation.setWebViewClient(new WebViewClient());
        WebSettings webSettings = holder.historyLocation.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = "https://www.google.com/maps/search/?api=1&query=" + currItem.getLocation();
        holder.historyLocation.loadUrl(url);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SearchHistoryViewHold extends RecyclerView.ViewHolder {
        private Button deleteItemBtn;
        private TextView searchText;
        private WebView historyLocation;

        public SearchHistoryViewHold(@NonNull View itemView) {
            super(itemView);
            this.deleteItemBtn = itemView.findViewById(R.id.delete_history_item);
            this.historyLocation = itemView.findViewById(R.id.history_location);
            this.searchText = itemView.findViewById(R.id.history_search_text);
        }
    }
}

package com.example.stackoverflowsearcher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stackoverflowsearcher.R;
import com.example.stackoverflowsearcher.models.Discussion;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHold>  {
    private List<Discussion> discussions;

    public SearchResultsAdapter(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    @NonNull
    @Override
    public SearchResultViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating a layout from XML and returning the holder

        //get inflater
        //the parent is actually the RecyclerView

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //by using inflater.inflate() you create your View from your XML file.
        View discussionView = inflater.inflate(R.layout.discussion_card_view, parent, false);

        // Return a new holder instance
        SearchResultViewHold viewHolder = new SearchResultViewHold(discussionView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHold holder, int position) {
        Discussion currDiscusion = this.discussions.get(position);
        holder.discussionTitle.setText(currDiscusion.getTitle());
        holder.discussionLink.setText(currDiscusion.getLink());
        Glide.with(holder.itemView.getContext())
                .load(currDiscusion.getOwner().getProfileImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .into(holder.discussionOwnerAvatar);
    }

    @Override
    public int getItemCount() {
        return this.discussions.size();
    }

    public class SearchResultViewHold extends RecyclerView.ViewHolder {
        private TextView discussionTitle;
        private TextView discussionLink;
        private ImageView discussionOwnerAvatar;

        public SearchResultViewHold(@NonNull View itemView) {
            super(itemView);
            this.discussionLink = itemView.findViewById(R.id.discussion_link);
            this.discussionTitle = itemView.findViewById(R.id.discussion_title);
            this.discussionOwnerAvatar = itemView.findViewById(R.id.owner_avatar);
        }

    }
}


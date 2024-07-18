package com.dts.haripertama.RestAPI;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dts.haripertama.R;

import java.util.List;

public class RestAPIRVAdapter extends RecyclerView.Adapter<RestAPIRVAdapter.PostViewHolder> {

    private List<PostModel> posts;

    public RestAPIRVAdapter(List<PostModel> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.daily_note, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostModel post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView body;

        public PostViewHolder(@NonNull android.view.View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitleDailyNote);
            body = itemView.findViewById(R.id.tvDescriptionDailyNote);
        }
    }
}

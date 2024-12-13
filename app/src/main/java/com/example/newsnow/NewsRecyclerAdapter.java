package com.example.newsnow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private List<Article> articleList;

    // Constructor to initialize the article list
    public NewsRecyclerAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    // Inflating the row layout for each article
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the row layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_row, parent, false);
        return new NewsViewHolder(view);
    }

    // Binding data to the views in each row
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        // Get the current article from the list
        Article article = articleList.get(position);

        // Set the article title and source
        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSourceName());

        // Load the article image using Picasso
        Picasso.get()
                .load(article.getUrlToImage())  // Set the image URL
                .error(R.drawable.no_image)     // Error placeholder in case the image cannot be loaded
                .placeholder(R.drawable.no_image) // Placeholder image while loading
                .into(holder.imageView);

        // Set onClickListener to navigate to the full article
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NewsFullActivity.class);
            intent.putExtra("url", article.getUrl()); // Pass the URL of the article
            v.getContext().startActivity(intent);
        });
    }

    // Method to update the data in the adapter
    public void updateData(List<Article> data) {
        // Clear the old list and add the new data
        this.articleList.clear();
        this.articleList.addAll(data);
        // Notify the adapter that the data set has changed
        notifyDataSetChanged();
    }

    // Return the total number of items
    @Override
    public int getItemCount() {
        return articleList.size();
    }

    // ViewHolder class to hold the views for each row
    static class NewsViewHolder extends RecyclerView.ViewHolder {
        // Views to display title, source, and image
        TextView titleTextView, sourceTextView;
        ImageView imageView;

        // Constructor to initialize the views
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Bind views to their corresponding IDs
            titleTextView = itemView.findViewById(R.id.article_title);
            sourceTextView = itemView.findViewById(R.id.article_source);
            imageView = itemView.findViewById(R.id.article_image_view);
        }
    }
}

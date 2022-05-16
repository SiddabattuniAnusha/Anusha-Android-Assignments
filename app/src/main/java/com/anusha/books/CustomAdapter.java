package com.anusha.books;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    Context context;
    List<Item> itemsList;

    public CustomAdapter(Context context, List<Item> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.design_of_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VolumeInfo volumeInfo = itemsList.get(position).volumeInfo;
        holder.textTitle.setText(volumeInfo.title);
        for (String author : volumeInfo.authors)
            holder.authorName.append(author + "\n");

        for (String category : volumeInfo.categories)
            holder.tvCategory.setText(category);

        Glide.with(context).load("https://i.postimg.cc/3rmSC7wS/book-stack.png").into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,MainActivity4.class).putExtra("item_id",itemsList.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textTitle;
        TextView authorName;
        TextView tvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.book_image);
            textTitle = itemView.findViewById(R.id.tv_title);
            authorName = itemView.findViewById(R.id.tv_author);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }
    }
}

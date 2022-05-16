package com.anusha.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity4 extends AppCompatActivity {
    ImageView imBook;
    TextView tvTittle, tvAuthors, tvDescription, tvCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        String itemId = getIntent().getStringExtra("item_id");

        imBook = findViewById(R.id.book_image);
        tvTittle = findViewById(R.id.tv_title);
        tvAuthors = findViewById(R.id.tv_author);
        tvDescription = findViewById(R.id.tv_description);
        tvCategory = findViewById(R.id.tv_category);

        List<Item> itemList = ApiResultRootHolder.root.items.stream().filter(item->item.id.equals(itemId)).collect(Collectors.toList());
        Glide.with(this).load(itemList.get(0).volumeInfo.imageLinks.smallThumbnail).into(imBook);

        VolumeInfo volumeInfo = itemList.get(0).volumeInfo;

        tvTittle.setText(volumeInfo.title);

        for (String author : volumeInfo.authors)
            tvAuthors.append(author + "\n");

        tvDescription.setText(volumeInfo.description);

        for(String category:volumeInfo.categories)
            tvCategory.setText(category);

    }
}
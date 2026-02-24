package com.example.myfirstapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView rvGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setLayoutManager(new LinearLayoutManager(this));

        List<GalleryItem> items = loadItemsFromAssets();
        GalleryAdapter adapter = new GalleryAdapter(this, items);
        rvGallery.setAdapter(adapter);
    }

    private List<GalleryItem> loadItemsFromAssets() {
        List<GalleryItem> items = new ArrayList<>();
        try {
            InputStream is = getAssets().open("gallery.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    items.add(new GalleryItem(parts[0], parts[1], parts[2]));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}

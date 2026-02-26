package com.example.myfirstapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;  // ИЗМЕНЕНО
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {  // ИЗМЕНЕНО: теперь extends Fragment

    private RecyclerView rvGallery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {  // ИЗМЕНЕНО: onCreateView вместо onCreate
        View view = inflater.inflate(R.layout.activity_gallery, container, false);  // ИЗМЕНЕНО

        rvGallery = view.findViewById(R.id.rvGallery);  // ИЗМЕНЕНО: view.findViewById
        rvGallery.setLayoutManager(new LinearLayoutManager(getContext()));  // ИЗМЕНЕНО: getContext()

        List<GalleryItem> items = loadItemsFromAssets();
        GalleryAdapter adapter = new GalleryAdapter(getContext(), items);  // ИЗМЕНЕНО: getContext()
        rvGallery.setAdapter(adapter);

        return view;  // ДОБАВЛЕНО
    }

    private List<GalleryItem> loadItemsFromAssets() {
        List<GalleryItem> items = new ArrayList<>();
        try {
            // ИЗМЕНЕНО: getActivity().getAssets() вместо getAssets()
            InputStream is = getActivity().getAssets().open("gallery.txt");
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
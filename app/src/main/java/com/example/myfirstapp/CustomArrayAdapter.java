// CustomArrayAdapter.java
package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<Task> {
    private final Context context;
    private final List<Task> objects;
    private float fontSize;

    public CustomArrayAdapter(Context context, List<Task> objects, float fontSize) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.objects = objects;
        this.fontSize = fontSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task item = objects.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setTypeface(Typeface.DEFAULT_BOLD); // Жирный шрифт
        textView.setTextSize(fontSize);              // Размер шрифта
        textView.setText(item.getTitle());           // Заголовок задачи
        return convertView;
    }
}


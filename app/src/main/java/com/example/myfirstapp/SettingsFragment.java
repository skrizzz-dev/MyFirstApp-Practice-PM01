package com.example.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {

    private Switch switchTheme;
    private SeekBar seekFont;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchTheme = view.findViewById(R.id.switchTheme);
        seekFont = view.findViewById(R.id.seekFont);

        prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

        boolean isDark = prefs.getBoolean("dark_theme", false);
        int fontSize = prefs.getInt("font_size", 16);

        switchTheme.setChecked(isDark);
        seekFont.setProgress(fontSize);

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_theme", isChecked).apply();
            Toast.makeText(requireContext(), "Тема сохранена", Toast.LENGTH_SHORT).show();
        });

        seekFont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prefs.edit().putInt("font_size", progress).apply();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return view;
    }
}



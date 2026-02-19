package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final int[] colors = {Color.RED, Color.GREEN, Color.BLUE};

    DatabaseHelper dbHelper;
    ListView listViewTasks;
    ArrayAdapter<String> adapter;
    List<Task> tasks = new ArrayList<>();
    int selectedTaskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        listViewTasks = findViewById(R.id.listViewTasks);

        ListView lvScreens = findViewById(R.id.lvScreens);
        String[] screens = {"Открыть профиль", "Открыть экран с расчётом", "Открыть экран настроек"};
        lvScreens.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, screens));
        lvScreens.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) startActivity(new Intent(this, ProfileActivity.class));
            else if (position == 1) startActivity(new Intent(this, CalcActivity.class));
            else if (position == 2) startActivity(new Intent(this, SettingsActivity.class));
        });

        // Добавить
        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            EditText etTitle = findViewById(R.id.etTitle);
            EditText etDesc = findViewById(R.id.etDesc);
            if (dbHelper.addTask(etTitle.getText().toString(), etDesc.getText().toString())) {
                Toast.makeText(this, "Задача добавлена!", Toast.LENGTH_SHORT).show();
                refreshList();
                etTitle.setText("");
                etDesc.setText("");
            }
        });

        // Обновить
        findViewById(R.id.btnRefresh).setOnClickListener(v -> refreshList());

        // Клик по задаче
        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {
            selectedTaskId = tasks.get(position).getId();
            Toast.makeText(this, "Выбрана задача ID: " + selectedTaskId, Toast.LENGTH_SHORT).show();
        });

        // Удалить
        findViewById(R.id.btnDeleteSelected).setOnClickListener(v -> {
            if (selectedTaskId != -1 && dbHelper.deleteTask(selectedTaskId)) {
                Toast.makeText(this, "Задача удалена!", Toast.LENGTH_SHORT).show();
                refreshList();
                selectedTaskId = -1;
            }
        });

        refreshList();
    }

    private void refreshList() {
        tasks.clear();
        tasks.addAll(dbHelper.getAllTasks());
        List<String> taskTitles = new ArrayList<>();
        for (Task task : tasks) {
            taskTitles.add(task.getTitle() + " (" + task.getDescription() + ")");
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskTitles);
        listViewTasks.setAdapter(adapter);
    }
}
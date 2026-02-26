package com.example.myfirstapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private ListView listViewTasks;
    private CustomArrayAdapter tasksAdapter;
    private List<Task> tasks = new ArrayList<>();

    private EditText etTitle;
    private EditText etDesc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dbHelper = new DatabaseHelper(requireContext());

        etTitle = view.findViewById(R.id.etTitle);
        etDesc = view.findViewById(R.id.etDesc);
        listViewTasks = view.findViewById(R.id.listViewTasks);

        view.findViewById(R.id.btnAdd).setOnClickListener(v -> addTask());
        view.findViewById(R.id.btnRefresh).setOnClickListener(v -> refreshTasksList());

        listViewTasks.setOnItemClickListener((parent, v, position, id) -> {
            int taskId = tasks.get(position).getId();
            Bundle args = new Bundle();
            args.putInt("TASK_ID", taskId);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_homeFragment_to_detailsFragment, args);
        });

        refreshTasksList();
        return view;
    }

    private void addTask() {
        String title = etTitle.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();

        if (title.length() < 3) {
            Toast.makeText(requireContext(), "Название слишком короткое", Toast.LENGTH_SHORT).show();
            return;
        }
        if (desc.isEmpty()) {
            Toast.makeText(requireContext(), "Добавьте описание", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.addTask(title, desc)) {
            Toast.makeText(requireContext(), "Задача добавлена", Toast.LENGTH_SHORT).show();
            etTitle.setText("");
            etDesc.setText("");
            refreshTasksList();
        } else {
            Toast.makeText(requireContext(), "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        }
    }


    private void refreshTasksList() {
        tasks.clear();
        tasks.addAll(dbHelper.getAllTasks());
        List<String> displayList = new ArrayList<>();
        for (Task task : tasks) {
            displayList.add("#" + task.getId() + " " + task.getTitle());
        }
        SharedPreferences prefs = requireContext().getSharedPreferences("settings",
                Context.MODE_PRIVATE);
        int fontSize = prefs.getInt("font_size", 16);
        tasksAdapter = new CustomArrayAdapter(requireContext(), tasks, fontSize);
        listViewTasks.setAdapter(tasksAdapter);
        tasksAdapter.notifyDataSetChanged();
    }
}

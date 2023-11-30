package com.example.myapplication99;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity {
    private List<Task> task_list;
    private boolean complete_tasks = false;
    private ArrayAdapter<Task> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task_list = new CopyOnWriteArrayList<>();
        loadTasks();
        ListView listView = findViewById(R.id.listViewTasks);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getDueTasks());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < task_list.size()) {
                    Task clickedTask = task_list.get(position);
                    if (clickedTask.isDone()) {
                        Log.d("TaskApp", "Before removal: " + task_list);
                        task_list.remove(clickedTask);
                        saveTasks();
                        updateAdapter();
                        Log.d("TaskApp", "After removal: " + task_list);
                    } else {
                        clickedTask.toggleStatus();
                        saveTasks();
                        updateAdapter();
                    }
                }
            }
        });
        Button addButton = findViewById(R.id.btnAddTask);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    private List<Task> getDueTasks() {
        List<Task> dueTasks = new ArrayList<>();
        for (Task task : task_list) {
            if (!task.isDone() || complete_tasks) {
                dueTasks.add(task);
            }
        }
        Log.d("TaskApp", "Due tasks: " + dueTasks.size());

        return dueTasks;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("newTask")) {
                Task newTask = (Task) data.getSerializableExtra("newTask");
                task_list.add(newTask);
                saveTasks();
                adapter.clear();
                adapter.addAll(getDueTasks());
                adapter.notifyDataSetChanged();
            }
        }
    }
    private void loadTasks() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        Type type = new TypeToken<ArrayList<Task>>() {
        }.getType();
        task_list = gson.fromJson(json, type);

        if (task_list == null) {
            task_list = new ArrayList<>();
        }

        Log.d("TaskApp", "Loaded tasks: " + task_list.size());
    }
    private void saveTasks() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(task_list);
        editor.putString("taskList", json);
        editor.apply();

        Log.d("TaskApp", "Saved tasks: " + task_list.size());
    }
    private void updateAdapter() {
        adapter.clear();
        adapter.addAll(getDueTasks());
        adapter.notifyDataSetChanged();
    }
}

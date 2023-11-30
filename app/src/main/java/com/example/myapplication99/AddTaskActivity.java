package com.example.myapplication99;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        final EditText editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        Button btnSaveTask = findViewById(R.id.btnSaveTask);
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = editTextTaskTitle.getText().toString();
                if (!taskTitle.isEmpty()) {
                    Task newTask = new Task(taskTitle);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newTask", newTask);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }
}

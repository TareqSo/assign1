package com.example.myapplication99;
import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable {
    private String title;
    private boolean isDone;

    public Task(String title) {
        this.title = title;
        this.isDone = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void toggleStatus() {
        isDone = !isDone;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(title, otherTask.title);
    }

    @Override
    public String toString() {
        return title;
    }
}

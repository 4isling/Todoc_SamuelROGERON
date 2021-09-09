package com.example.todoc.model;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Comparator;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "project_id",
        childColumns = "project_id"))
public class Task {

    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "project_id")
    public long project_id;

    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    @ColumnInfo(name = "task_name")
    public String name;

    /**
     * The timestamp when the task has been created
     */
    @SuppressWarnings("NullableProblems")
    public long creationTimestamp;

    private static Calendar cal = Calendar.getInstance();
    private static long CREATION_TIMESTAMP = cal.getTimeInMillis();

    public Task(){}

    public Task(@NonNull String name) {
        this.name = name;
    }

    public Task(long projectId, @NonNull String name, long creationTimestamp) {
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    // --- GETTER ---

    @Nullable
    public Project getProject() {
        return Project.getProjectById(project_id);
    }

    public long getProjectId(){return this.project_id;}

    public long getId() {
        return this.id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public long getCreationTimestamp(){
        return this.creationTimestamp;
    }

    // --- SETTER ---

    public void setId(long id) {
        this.id = id;
    }
    public void setProjectId(long projectId) {
        this.project_id = projectId;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    // --- Comparator ---

    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }

    public static Task fromContentValues(ContentValues values){
        final Task task = new Task();
        if (values.containsKey("name")) task.setName(values.getAsString("name"));
        if (values.containsKey("project_id")) task.setProjectId(values.getAsLong("project_id"));
        if (values.containsKey("creationTimestamp")) task.setCreationTimestamp(CREATION_TIMESTAMP);
        return task;
    }
}
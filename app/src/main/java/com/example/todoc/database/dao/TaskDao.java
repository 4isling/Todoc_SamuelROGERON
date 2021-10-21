package com.example.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todoc.model.Task;

import java.util.List;

@Dao
public interface  TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTask(Task task);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM Task WHERE task_id = :task_id")
    LiveData<Task> getTask(long task_id);

    @Delete
    int deleteTask(Task task);

    @Query("SELECT * FROM Task ORDER BY task_name ASC")
    LiveData<List<Task>> getTaskAZ();

    @Query("SELECT * FROM Task ORDER BY task_name DESC")
    LiveData<List<Task>> getTaskZA();

    @Query("SELECT * FROM task ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTaskRecentFirst();

    @Query("SELECT * FROM task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTaskOldFirst();
}

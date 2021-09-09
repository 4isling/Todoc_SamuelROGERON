package com.example.todoc.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoc.model.Task;

import java.util.List;

@Dao
public interface  TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTask(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT project_id FROM Task WHERE task_id = :task_id")
    LiveData<Long> getTaskProject(Long task_id);

    @Query("SELECT * FROM Task WHERE task_id = :task_id")
    LiveData<List<Task>> getTask(long task_id);

    @Query("SELECT * FROM Task WHERE task_id = :task_id")
    Cursor getTaskWithCursor(long task_id);

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Delete
    int deleteTask(Task task);

    @Query("DELETE FROM Task WHERE task_id = :task_id")
    int deleteTask(long task_id);
}

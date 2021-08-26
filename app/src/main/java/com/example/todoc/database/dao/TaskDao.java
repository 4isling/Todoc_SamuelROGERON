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
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTask(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM Task WHERE project_id = :project_id")
    LiveData<List<Task>> getTaskByProject(long project_id);

    @Query("SELECT * FROM Task WHERE project_id = :project_id")
    Cursor getTasksWithCursor(long project_id);

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Delete
    void deleteTask(long taskId);
}

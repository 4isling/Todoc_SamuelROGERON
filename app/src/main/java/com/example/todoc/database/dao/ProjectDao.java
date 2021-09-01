package com.example.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todoc.model.Project;
import com.example.todoc.model.Task;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    @Query("SELECT * FROM Project WHERE project_id = :project_id")
    LiveData<Project> getProject(long project_id);

    @Delete
    void deleteProject(Project project);



}
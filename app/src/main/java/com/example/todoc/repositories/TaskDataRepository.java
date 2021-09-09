package com.example.todoc.repositories;

import android.database.Cursor;

import androidx.lifecycle.LiveData;

import com.example.todoc.database.dao.TaskDao;
import com.example.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTask(long task_id){
        return this.taskDao.getTask(task_id);
    }

    public Cursor getTaskWithCursor(Long task_id){
        return this.taskDao.getTaskWithCursor(task_id);
    }

    public LiveData<List<Task>> getAllTask(){
        return this.taskDao.getAllTask();
    }

    public void createTask(Task task){
        this.taskDao.createTask(task);
    }

    public long insertTask(Task task){
        return this.taskDao.insertTask(task);
    }

    public int updateTask(Task task){
        return this.taskDao.updateTask(task);
    }

    public LiveData<Long> getTaskProject(long task_id){
        return this.taskDao.getTaskProject(task_id);
    }

    public void deleteTask(long task_id){
        this.taskDao.deleteTask(task_id);
    }
}

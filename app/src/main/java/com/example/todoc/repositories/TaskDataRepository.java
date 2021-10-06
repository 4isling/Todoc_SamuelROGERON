package com.example.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.example.todoc.database.dao.TaskDao;
import com.example.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public LiveData<Task> getTask(long task_id){
        return this.taskDao.getTask(task_id);
    }

    public LiveData<List<Task>> getAllTask(){
        return this.taskDao.getAllTask();
    }

    public void createTask(Task task){
        this.taskDao.createTask(task);
    }


    public void deleteTask(Task task){
        this.taskDao.deleteTask(task);
    }
}

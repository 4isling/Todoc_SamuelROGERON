package com.example.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoc.model.Task;
import com.example.todoc.repositories.TaskDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    private int sortInt = 5;
    private LiveData<List<Task>> tasksLiveData;
    private ArrayList<List<Task>> taskArrayList;

    // DATA
    @Nullable
    private LiveData<Task> currentTask;

    public TaskViewModel(TaskDataRepository taskDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    // -------------
    // FOR task
    // -------------

    public LiveData<List<Task>> getTasks(){
        if(tasksLiveData == null){
            return taskDataSource.getAllTask();
        }else {
            return tasksLiveData;
        }
    }

    public void createTask(Task task) {
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(Task task) {
        executor.execute(() -> {
            taskDataSource.deleteTask(task);
        });
    }

    // -------------
    // FOR sort
    // -------------
    public LiveData<List<Task>> configSortList(){
        int sortInt = getSortInt();
        switch (sortInt){
            case 0:
                this.tasksLiveData = taskDataSource.getTaskAZ();
                break;
            case 1:
                this.tasksLiveData = taskDataSource.getTaskZA();
                break;
            case 2:
                this.tasksLiveData = taskDataSource.getTaskRecentFirst();
                break;
            case 3:
                this.tasksLiveData = taskDataSource.getTaskOldFirst();
                break;
            default:
                this.tasksLiveData = taskDataSource.getAllTask();
                break;
        }
        return tasksLiveData;
    }

    // mettre la logique de tri dans le view model
    // tirer la liste des taches recupere les taches

    public LiveData<List<Task>> setSort(int sortInt) {
        this.sortInt = sortInt;
        return configSortList();
    }

    public int getSortInt() {
        return this.sortInt;
    }



//surement rajouter le tri ici
    public void init(long task_id) {
        if (this.currentTask != null){
            return;
        }
        currentTask = taskDataSource.getTask(task_id);
        tasksLiveData = taskDataSource.getAllTask();
    }
}

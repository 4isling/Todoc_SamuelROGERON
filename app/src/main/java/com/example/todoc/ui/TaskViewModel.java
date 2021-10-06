package com.example.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoc.model.Task;
import com.example.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskDataRepository taskDataSource;

    private final Executor executor;

    private enum SortMethod {
        ALPHABETICAL,
        ALPHABETICAL_INVERTED,
        RECENT_FIRST,
        OLD_FIRST,
        NONE
    }

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

    public LiveData<Task> getTask(long task_id) {
        return taskDataSource.getTask(task_id);
    }

    public LiveData<List<Task>> getTasks(){
        return taskDataSource.getAllTask();
    }

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(Task task) {
        executor.execute(() -> {
            taskDataSource.deleteTask(task);
        });
    }

    // -------------
    // FOR sort
    // -------------


//surement rajouter le tri ici
    public void init(long task_id) {
        if (this.currentTask != null){
            return;
        }
        currentTask = taskDataSource.getTask(task_id);

       // this.SortMethod = sorting;
    }
}

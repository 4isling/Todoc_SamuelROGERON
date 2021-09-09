package com.example.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoc.model.Project;
import com.example.todoc.model.Task;
import com.example.todoc.repositories.ProjectDataRepository;
import com.example.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<List<Task>> currentTask;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init() {
        currentTask = taskDataSource.getAllTask();
    }

    // -------------
    // FOR project
    // -------------

    public LiveData<Project> getProject(long projectId) {
        return projectDataSource.getProject(projectId) ;
    }

    // -------------
    // FOR task
    // -------------

    public LiveData<List<Task>> getTask(long projectId) {
        return taskDataSource.getTask(projectId);
    }

    public LiveData<List<Task>> getTasks(){
        return taskDataSource.getAllTask();
    }

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> taskDataSource.deleteTask(taskId));
    }

    public void updateTask(Task task) {
        executor.execute(() -> taskDataSource.updateTask(task));
    }
}

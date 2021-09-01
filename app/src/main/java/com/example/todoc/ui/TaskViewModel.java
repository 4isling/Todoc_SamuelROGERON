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
    private LiveData<Project> currentProject;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init(long projectId) {
        if (this.currentProject != null) {
            return;
        }
        currentProject = projectDataSource.getProject(projectId);
    }

    // -------------
    // FOR project
    // -------------

    public LiveData<Project> getProject(long projectId) { return this.currentProject;  }

    // -------------
    // FOR task
    // -------------

    public LiveData<List<Task>> getTasks(long projectId) {
        return taskDataSource.getTask(projectId);
    }

    public void createTask(Task task) {
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> {
            taskDataSource.deleteTask(taskId);
        });
    }

    public void updateTask(Task task) {
        executor.execute(() -> {
            taskDataSource.updateTask(task);
        });
    }
}

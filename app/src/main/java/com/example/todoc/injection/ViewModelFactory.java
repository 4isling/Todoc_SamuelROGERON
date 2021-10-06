package com.example.todoc.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoc.repositories.TaskDataRepository;
import com.example.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    public ViewModelFactory(TaskDataRepository taskDataSource, Executor executor){
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)){
            return (T) new TaskViewModel(taskDataSource, executor);
        }
        throw new IllegalArgumentException("Unknow ViewModel class");
    }
}

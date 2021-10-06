package com.example.todoc.injection;

import android.content.Context;

import com.example.todoc.database.SaveMyTaskDatabase;
import com.example.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskDataRepository provideTaskDataSource(Context context){
        SaveMyTaskDatabase database = SaveMyTaskDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);;
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, executor);
    }
}

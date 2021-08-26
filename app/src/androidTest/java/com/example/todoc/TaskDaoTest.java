package com.example.todoc;

import android.app.Instrumentation;
import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.todoc.database.SaveMyTaskDatabase;
import com.example.todoc.database.dao.TaskDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class TaskDaoTest {
    private TaskDao taskDao;
    private SaveMyTaskDatabase database;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, SaveMyTaskDatabase.class).build();
        taskDao = database.taskDao();
    }


    @After
    public void closeDb() throws Exception {
        database.close();
    }
}

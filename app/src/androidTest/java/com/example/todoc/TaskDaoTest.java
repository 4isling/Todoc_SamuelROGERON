package com.example.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.todoc.database.SaveMyTaskDatabase;
import com.example.todoc.model.Project;
import com.example.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)

public class TaskDaoTest {
    private SaveMyTaskDatabase database;
    private static final Calendar cal = Calendar.getInstance();
    private static final long CREATION_TIMESTAMP = cal.getTimeInMillis();
    private static final long PROJECT_ID = 8;
    private final Project PROJECT_DEMO = new Project(PROJECT_ID,"project_test", 0xFF0000FF);
    private static final Task TASK_DEMO1 = new Task(PROJECT_ID, "task_test", CREATION_TIMESTAMP);
    private static final Task TASK_DEMO2 = new Task(PROJECT_ID, "task_test2", Math.addExact(CREATION_TIMESTAMP,1L));


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                SaveMyTaskDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTask() throws  InterruptedException{
        this.database.taskDao().createTask(TASK_DEMO1);
        this.database.taskDao().createTask(TASK_DEMO2);


        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask());
        assertTrue(tasks.size() == 2);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException{
        this.database.taskDao().createTask(TASK_DEMO1);
        Task added_task = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask()).get(0);

        this.database.taskDao().deleteTask(added_task);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask());
        assertTrue(tasks.isEmpty());
    }

}

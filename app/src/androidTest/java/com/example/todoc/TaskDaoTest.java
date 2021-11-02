package com.example.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.todoc.database.SaveMyTaskDatabase;
import com.example.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)

public class TaskDaoTest {
    private SaveMyTaskDatabase database;
    private static final Calendar cal = Calendar.getInstance();
    private static final long CREATION_TIMESTAMP = cal.getTimeInMillis();
    private static final long PROJECT_ID = 1;
    private static Task TASK_DEMO1 = new Task(3,PROJECT_ID, "Aaa", CREATION_TIMESTAMP);
    private static Task TASK_DEMO3 = new Task(1,PROJECT_ID, "Ccc", Math.addExact(CREATION_TIMESTAMP,1L));
    private static Task TASK_DEMO2 = new Task(2,PROJECT_ID, "Bbb", Math.addExact(CREATION_TIMESTAMP,2L));


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
        database.clearAllTables();
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
        assertEquals(tasks.size(), 2);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException{
        this.database.taskDao().createTask(TASK_DEMO1);
        Task added_task = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask()).get(0);

        this.database.taskDao().deleteTask(added_task);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTask());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndSortAZ() throws InterruptedException{
        this.database.taskDao().createTasks(TASK_DEMO3, TASK_DEMO1, TASK_DEMO2);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTaskAZ());

        assertEquals(tasks.get(0).getName() , TASK_DEMO1.getName());
        assertEquals(tasks.get(1).getName(), TASK_DEMO2.getName());
        assertEquals(tasks.get(2).getName(), TASK_DEMO3.getName());
    }

    @Test
    public void insertAndSortZA() throws InterruptedException{
        this.database.taskDao().createTask(TASK_DEMO3);
        this.database.taskDao().createTask(TASK_DEMO1);
        this.database.taskDao().createTask(TASK_DEMO2);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTaskZA());

        assertEquals(tasks.get(0).getName(), TASK_DEMO3.getName());
        assertEquals(tasks.get(1).getName(), TASK_DEMO2.getName());
        assertEquals(tasks.get(2).getName(), TASK_DEMO1.getName());

    }

    @Test
    public void insertAndSortRecentFirst() throws InterruptedException {
        this.database.taskDao().createTask(TASK_DEMO1);
        this.database.taskDao().createTask(TASK_DEMO2);
        this.database.taskDao().createTask(TASK_DEMO3);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTaskRecentFirst());

        assertEquals(tasks.get(0).getCreationTimestamp(), TASK_DEMO1.getCreationTimestamp());
        assertEquals(tasks.get(1).getCreationTimestamp(), TASK_DEMO3.getCreationTimestamp());
        assertEquals(tasks.get(2).getCreationTimestamp(), TASK_DEMO2.getCreationTimestamp());
    }

    @Test
    public void insertAndSortOldFirst() throws InterruptedException {
        this.database.taskDao().createTask(TASK_DEMO1);
        this.database.taskDao().createTask(TASK_DEMO2);
        this.database.taskDao().createTask(TASK_DEMO3);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTaskOldFirst());

        assertEquals(tasks.get(0).getCreationTimestamp(), TASK_DEMO2.getCreationTimestamp());
        assertEquals(tasks.get(1).getCreationTimestamp(), TASK_DEMO3.getCreationTimestamp());
        assertEquals(tasks.get(2).getCreationTimestamp(), TASK_DEMO1.getCreationTimestamp());
    }
}

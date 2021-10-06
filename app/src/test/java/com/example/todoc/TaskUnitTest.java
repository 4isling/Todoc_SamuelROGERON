package com.example.todoc;

import com.example.todoc.model.Task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertSame;

/**
 * test unit à faire test des differents tri : AZ , ZA , OLD NEW , NEW OLD.
 * test projects
 */
public class TaskUnitTest {
    final Task task1 = new Task(1,1, "a", 123);
    final Task task2 = new Task(2,1,"b", 321);
    final Task task3 = new Task(3,1,"c", 231);



    @Test
    public void testSortZA(){
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());
        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(3), task1);
    }

    @Test
    public void testSortAZ(){
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        Collections.sort(tasks, new Task.TaskAZComparator());
        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }

    @Test
    public void testSortOldNew(){
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        Collections.sort(tasks, new Task.TaskOldComparator());
        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void testSortNewOld(){
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        Collections.sort(tasks, new Task.TaskRecentComparator());
        assertSame(tasks.get(0),task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_project(){
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

    }
}

package com.example.todoc;

import com.example.todoc.model.Project;
import com.example.todoc.model.Task;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * test unit à faire test des differents tri : AZ , ZA , OLD NEW , NEW OLD.
 * test projects
 */
public class TaskUnitTest {
    @Test
    public void test_projects() {
        final Task task1 = new Task(1, 1, "task 1", new Date().getTime());
        final Task task2 = new Task(2, 2, "task 2", new Date().getTime());
        final Task task3 = new Task(3, 3, "task 3", new Date().getTime());
        final Task task4 = new Task(4, 4, "task 4", new Date().getTime());

        assertEquals("Projet Tartampion", Project.getProjectById(task1.getProjectId()).getName());
        assertEquals("Projet Lucidia", Project.getProjectById(task2.getProjectId()).getName());
        assertEquals("Projet Circus", Project.getProjectById(task3.getProjectId()).getName());
        assertEquals(null, Project.getProjectById(task4.getProjectId()).getName());
    }


}

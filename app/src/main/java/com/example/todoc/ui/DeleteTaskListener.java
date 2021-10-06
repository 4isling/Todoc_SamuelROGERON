package com.example.todoc.ui;

import com.example.todoc.model.Task;

public interface DeleteTaskListener {
    /**
     * Called when a task needs to be deleted.
     *
     * @param task the task that needs to be deleted
     */
    void onDeleteTask(Task task);
}

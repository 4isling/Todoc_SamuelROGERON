package com.example.todoc.ui;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.example.todoc.model.Project;
import com.example.todoc.model.Task;

/**
 * <p>ViewHolder for task items in the tasks list</p>
 *
 * @author Gaëtan HERFRAY
 */
class TaskViewHolder extends RecyclerView.ViewHolder {
    /**
     * The circle icon showing the color of the project
     */
    private final AppCompatImageView imgProject;

    /**
     * The TextView displaying the name of the task
     */
    private final TextView lblTaskName;

    /**
     * The TextView displaying the name of the project
     */
    private final TextView lblProjectName;

    /**
     * The delete icon
     */
    private final AppCompatImageView imgDelete;

    /**
     * The listener for when a task needs to be deleted
     */
    private final TasksAdapter.DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TaskViewHolder.
     *
     * @param itemView the view of the task item
     * @param deleteTaskListener the listener for when a task needs to be deleted to set
     */
    TaskViewHolder(@NonNull View itemView, @NonNull TasksAdapter.DeleteTaskListener deleteTaskListener) {
        super(itemView);

        this.deleteTaskListener = deleteTaskListener;

        imgProject = itemView.findViewById(R.id.img_project);
        lblTaskName = itemView.findViewById(R.id.lbl_task_name);
        lblProjectName = itemView.findViewById(R.id.lbl_project_name);
        imgDelete = itemView.findViewById(R.id.img_delete);

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Object tag = view.getTag();
                if (tag instanceof Task) {
                    TaskViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
                }
            }
        });
    }

    /**
     * Binds a task to the item view.
     *
     * @param task the task to bind in the item view
     */
    void bind(Task task) {
        lblTaskName.setText(task.getName());
        imgDelete.setTag(task);

        final Project taskProject = task.getProject();
        if (taskProject != null) {
            imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
            lblProjectName.setText(taskProject.getName());
        } else {
            imgProject.setVisibility(View.INVISIBLE);
            lblProjectName.setText("");
        }

    }
}
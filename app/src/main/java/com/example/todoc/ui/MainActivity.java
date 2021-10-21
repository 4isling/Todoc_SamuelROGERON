package com.example.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.example.todoc.injection.Injection;
import com.example.todoc.injection.ViewModelFactory;
import com.example.todoc.model.Project;
import com.example.todoc.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements DeleteTaskListener {
    /**
     * List of all projects available in the application
     */
    private List<Project> allProjects;

    /**
     * List of all current tasks of the application
     */
    @NonNull
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * The adapter which handles the list of tasks
     */
    private TasksAdapter adapter;

    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;

    /**
     * Dialog to create a new task
     */
    @Nullable
    public AlertDialog dialog = null;

    /**
     * EditText that allows user to set the name of a task
     */
    @Nullable
    private EditText dialogEditText = null;

    /**
     * Spinner that allows the user to associate a project to a task
     */
    @Nullable
    private Spinner dialogSpinner = null;

    /**
     * The RecyclerView which displays the list of tasks
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;

    /**
     * The TextView displaying the empty state
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    // FOR DATA
    private TaskViewModel taskViewModel;

    private final String sortTypeKey = "sortTypeKey";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate", "onCreate");

        setContentView(R.layout.activity_main);

        listTasks = findViewById(R.id.list_tasks);

        this.configureViewModel();
        this.configureRecyclerView();

        lblNoTasks = findViewById(R.id.lbl_no_task);
        findViewById(R.id.fab_add_task).setOnClickListener(view -> showAddTaskDialog());

        this.getProjects();
        this.getTasks();
    }
/*
      @Override protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
      super.onSaveInstanceState(savedInstanceState);
      savedInstanceState.putInt(sortTypeKey,sortInt);
      }

      @Override protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
      super.onRestoreInstanceState(savedInstanceState);
      Log.i("onRestore", "onRestore");
      this.sortInt = savedInstanceState.getInt(sortTypeKey);
      this.sortConfig();
      }
*/

    // UI
    private void configureRecyclerView(){
        this.adapter = new TasksAdapter(tasks,this);
        this.listTasks.setAdapter(this.adapter);
        this.listTasks.setLayoutManager(new LinearLayoutManager(this));
    }
    //DATA
    private void getProjects() {
        this.allProjects = Arrays.asList(Project.getAllProjects());
    }

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.taskViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TaskViewModel.class);
        this.taskViewModel.init(1L);
    }



    private void getTasks() {
        this.taskViewModel.getTasks().observe(this, this::updateTasks);
    }

    private void deleteTask(Task task) {
        this.taskViewModel.deleteTask(task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            taskViewModel.setSort(0);
        } else if (id == R.id.filter_alphabetical_inverted) {
            taskViewModel.setSort(1);
        } else if (id == R.id.filter_oldest_first) {
            taskViewModel.setSort(3);
        } else if (id == R.id.filter_recent_first) {
            taskViewModel.setSort(2);
        }
        updateTask();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(Task task) {
        tasks.remove(task);
        deleteTask(task);
        updateTask();
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {

            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }

            // If both project and name of the task have been set
            else if (taskProject != null) {
                Task task = new Task(taskProject.getId(),
                        taskName,
                        new Date().getTime()
                );
                this.taskViewModel.createTask(task);
                addTask(task);
                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else {
                dialogInterface.dismiss();
            }
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    /**
     * Adds the given task to the list of created tasks.
     *
     * @param task the task to be added to the list
     */
    private void addTask(@NonNull Task task) {
        this.tasks.add(task);
        updateTask();
    }


    /**
     * Updates the list of tasks in the UI
     */
    private void updateTask() {
        if (tasks.size() == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
  /*          switch (sortMethod) {
                case ALPHABETICAL:
                    taskViewModel.setSort(0);
                    getTasks();
                    break;
                case ALPHABETICAL_INVERTED:
                    taskViewModel.setSort(1);
                    getTasks();
                    break;
                case RECENT_FIRST:
                    taskViewModel.setSort(2);
                    getTasks();
                    break;
                case OLD_FIRST:
*/                    getTasks();
    //                break;
            }
          adapter.updateTasks(tasks);
     //   }
    }

    private void updateTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        if (tasks.size() > 0) {
            lblNoTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.VISIBLE);
        }
        this.adapter.updateTasks(tasks);
    }

    /**
     * Returns the dialog allowing the user to create a new task.
     *
     * @return the dialog allowing the user to create a new task
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });

        return dialog;
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

    /**
     * List of all possible sort methods for task
     */
    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }
}

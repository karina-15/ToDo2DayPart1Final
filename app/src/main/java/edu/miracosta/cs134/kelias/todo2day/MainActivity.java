package edu.miracosta.cs134.kelias.todo2day;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import edu.miracosta.cs134.kelias.todo2day.model.DBHelper;
import edu.miracosta.cs134.kelias.todo2day.model.Task;

public class MainActivity extends AppCompatActivity {

    // Create a reference to the database
    private DBHelper mDB;
    private List<Task> mAllTasks;

    private EditText descriptionEditText;
    private ListView taskListView;
    private TaskListAdapter mTaskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // wire up the views
        descriptionEditText = findViewById(R.id.taskEditText);
        taskListView = findViewById(R.id.taskListView);

        mDB = new DBHelper(this);
        // mDB.clearAllTasks();

/*        mDB.addTask(new Task("Complete HW 4"));
        mDB.addTask(new Task("Review show-and-tell speech"));
        mDB.addTask(new Task("Study for Quiz #2 for c++"));
        mDB.addTask(new Task("Study for test tomorrow morning"));*/

        mAllTasks = mDB.getAllTasks();

        // Instantiate the ListAdapter
        mTaskListAdapter = new TaskListAdapter(this, R.layout.task_item, mAllTasks);
        // connect the ListView with the ListAdapter
        taskListView.setAdapter(mTaskListAdapter);

        // for debugging
        // Let's loop through them and print them to the log
        for (Task t : mAllTasks) {
            Log.i("ToDo2Day", t.toString());
        }

        mDB.clearAllTasks();

        // Let's create some dummy data and add it to database to test if it currently works
        // Create 4 new tasks and use the methods in DBHelper to add them to our database.
        Log.i("ToDo2Day", "Creating 4 Tasks");
        mDB.addTask(new Task("Complete HW 4"));
        mDB.addTask(new Task("Review show-and-tell speech"));
        mDB.addTask(new Task("Study for Quiz #2 for c++"));
        mDB.addTask(new Task("Study for test tomorrow morning"));

        // Use the getAllTasks() method to print each task to our Android Monitor using Log.i(...)
        mAllTasks = mDB.getAllTasks();
        Log.i("ToDo2Day", "All Tasks");
        for (Task t : mAllTasks) {
            Log.i("ToDo2Day", t.toString());
        }

        // Delete 2 tasks
        Log.i("ToDo2Day", "Deleting " + mAllTasks.get(0).toString());
        mDB.deleteTask(mAllTasks.get(0));
        Log.i("ToDo2Day", "Deleting " + mAllTasks.get(2).toString());
        mDB.deleteTask(mAllTasks.get(2));

        // Use the getAllTasks() method to print each task to our Android Monitor using Log.i(... )
        mAllTasks = mDB.getAllTasks();
        Log.i("ToDo2Day", "All Tasks");
        for (Task t : mAllTasks) {
            Log.i("ToDo2Day", t.toString());
        }

        // Update 1 task (e.g. change description and it's done status)
        Log.i("ToDo2Day", "Updating task");
        Task mUpdatedTask = mAllTasks.get(0);
        Log.i("ToDo2Day", mUpdatedTask.toString());
        mUpdatedTask.setDescription("Prepare for cultural exchange");
        mUpdatedTask.setIsDone(true);
        mDB.updateTask(mUpdatedTask);

        // Use the getAllTasks() method to print each task to our Android Monitor using Log.i(... )
        mAllTasks = mDB.getAllTasks();
        Log.i("ToDo2Day", "All Tasks");
        for (Task t : mAllTasks) {
            Log.i("ToDo2Day", t.toString());
        }

        // Finally, use the getSingleTask(int id) method to get a specific Task from the database,
        // then print it to our Android Monitor using Log.i(... )
        Task mSingleTask = mDB.getSingleTask(2);
        Log.i("ToDo2Day", "Get single task");
        Log.i("ToDo2Day", mSingleTask.toString());

    }

    public void addTask(View v)
    {
        // Let's extract the description from the EditText
        String description = descriptionEditText.getText().toString();

        // id = -1, description = User input, isDone = false;
        Task newTask = new Task(description);
        long id = mDB.addTask(newTask);
        newTask.setId(id);
        // Add the new task to the list
        mAllTasks.add(newTask);
    }

    // only want database to close after closing app not while using the app
    // Ctrl + o - onDestroy to close database when app is closed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDB.close();
    }
}

package edu.miracosta.cs134.kelias.todo2day;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.miracosta.cs134.kelias.todo2day.model.Task;

public class TaskListAdapter extends ArrayAdapter<Task> {
    private Context mContext;
    private int mResource;
    private List<Task> mAllTasks;

    public TaskListAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mAllTasks = objects;
    }

    // override a method called getView ctrl+o
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, null);
        CheckBox isDoneCheckbox = view.findViewById(R.id.isDoneCheckBox);

        Task selectedTask = mAllTasks.get(position);
        isDoneCheckbox.setChecked(selectedTask.isIsDone());
        isDoneCheckbox.setText(selectedTask.getDescription());

        return view;
    }
}

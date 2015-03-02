package com.greenapp.todolist.to_dolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kabardinov133238 on 06.02.2015.
 * какой то образцовый адаптер ё моё
 */
public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    int resource;

    public ToDoItemAdapter(Context context, int resource, List<ToDoItem> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("myLogs", "getView");
        LinearLayout todoView;
        ToDoItem item = getItem(position);

        String taskString = item.getTask();
        Date createdDate = item.getCreated();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(createdDate);


        if (convertView == null) {
            Log.d("myLogs", "1");
            todoView = new LinearLayout(getContext());
            if (todoView == null) Log.d("myLogs", "null");
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource,todoView,true);

        } else {
            Log.d("myLogs", "2");
            todoView = (LinearLayout) convertView;
        }
        Log.d("myLogs", "2.5");
        TextView dateView = (TextView) todoView.findViewById(R.id.rowDate);
        TextView taskView = (TextView) todoView.findViewById(R.id.row);
        Log.d("myLogs", "2.7");
        Log.d("myLogs", dateString);
        Log.d("myLogs", "" + dateView.getId());
        dateView.setText(dateString);
        Log.d("myLogs", "2.8");
        taskView.setText(taskString);
        Log.d("myLogs", "3");
        return todoView;
    }

}

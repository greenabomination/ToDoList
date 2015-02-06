package com.greenapp.todolist.to_dolist;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kabardinov133238 on 06.02.2015.
 */
public class ToDoItem {

    String task;
    Date created;


    public String getTask() {
        return task;
    }

    public Date getCreated() {
        return created;
    }

    public  ToDoItem(String _task, Date _created) {
        task = _task;
        created = _created;
    }

    public ToDoItem(String _task) {
        this(_task, new Date(java.lang.System.currentTimeMillis()));
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy");
        String dateString = sdf.format(created);
        return "(" + dateString + ")" + task;
    }
}

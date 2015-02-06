package com.greenapp.todolist.to_dolist;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ToDoListActivity extends Activity implements NewItemFragment.OnNewItemAddedListener {

    private ToDoItemAdapter aa;
    private ArrayList<ToDoItem> todoItems;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate your view
        setContentView(R.layout.activity_to_do_list);

        // Get references to the Fragments
        FragmentManager fm = getFragmentManager();
        ToDoListFragment todoListFragment =
                (ToDoListFragment) fm.findFragmentById(R.id.ToDoListFragment);

        // Create the array list of to do items
        todoItems = new ArrayList<ToDoItem>();

        // Create the array adapter to bind the array to the ListView
        int resID = R.layout.todolist_item;
        aa = new ToDoItemAdapter(this, resID, todoItems);

        // Bind the array adapter to the ListView.
        todoListFragment.setListAdapter(aa);
    }

    public void onNewItemAdded(String newItem) {
        ToDoItem newToDoItem = new ToDoItem(newItem);
        todoItems.add(0, newToDoItem);
        aa.notifyDataSetChanged();
    }

}

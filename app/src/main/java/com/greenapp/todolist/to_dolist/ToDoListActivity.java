package com.greenapp.todolist.to_dolist;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class ToDoListActivity extends Activity implements NewItemFragment.OnNewItemAddedListener {

    final ArrayList<String> todoItems = new ArrayList<String>();
    final ArrayAdapter<String> aa;

    {

        aa = new ArrayAdapter<String>(this, R.layout.todolist_item, todoItems);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        FragmentManager fm = getFragmentManager();
        ToDoListFragment toDoListFragment = (ToDoListFragment) fm.findFragmentById(R.id.ToDoListFragment);


        toDoListFragment.setListAdapter(aa);


    }

    public void onNewItemAdded(String newItem) {
        todoItems.add(newItem);
        aa.notifyDataSetChanged();
    }
}

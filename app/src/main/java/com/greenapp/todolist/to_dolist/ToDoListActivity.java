package com.greenapp.todolist.to_dolist;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ToDoListActivity extends Activity implements NewItemFragment.OnNewItemAddedListener,
        LoaderManager.LoaderCallbacks<Cursor> {

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
        Log.d("myLogs", "preLoadManager");
        getLoaderManager().initLoader(0, null, this);
    }

    public void onResume() {
        Log.d("myLogs", "onResume");
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    public void onNewItemAdded(String newItem) {
        Log.d("myLogs", "onNewItemAdded");
        ContentResolver cr = getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(ToDoContentProvider.KEY_TASK, newItem);
        cr.insert(ToDoContentProvider.CONTENT_URI, cv);
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("myLogs", "createLoader");
        CursorLoader loader = new CursorLoader(this,
                ToDoContentProvider.CONTENT_URI, null, null, null, null);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("myLogs", "LoadFinish");
        int keyTaskIndex = data.getColumnIndexOrThrow(ToDoContentProvider.KEY_TASK);
        todoItems.clear();
        Log.d("myLogs", "LoadFinish1");
        while (data.moveToNext()) {
            Log.d("myLogs", "LoadFinish2");
            ToDoItem newItem = new ToDoItem(data.getString(keyTaskIndex));
            todoItems.add(newItem);
            Log.d("myLogs", "LoadFinish3");
        }
        aa.notifyDataSetChanged();
        Log.d("myLogs", "LoadFinish4");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

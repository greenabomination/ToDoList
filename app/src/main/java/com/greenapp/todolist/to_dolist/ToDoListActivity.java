package com.greenapp.todolist.to_dolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ToDoListActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        ListView myListView = (ListView) findViewById(R.id.myListView);
        final EditText myEditText = (EditText) findViewById(R.id.myEditText);

        final ArrayList<String> todoItems = new ArrayList<String>();
        final ArrayAdapter<String> aa;

        {
            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

        }

        myListView.setAdapter(aa);
        myEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_ENTER) || (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)) {
                        todoItems.add(0, myEditText.getText().toString());
                        aa.notifyDataSetChanged();
                        myEditText.setText("");
                        return true;
                    }
                return false;
            }
        });
    }


}

package org.snake.android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.snake.android.adapter.TodoItemsAdapter;
import org.snake.android.datamodels.TodoItemsDBHelper;
import org.snake.android.datamodels.TodoObject;

import java.text.ParseException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TodoItemsDBHelper db;
    List<TodoObject> todoItems;
    TodoItemsAdapter customItemsadapter;
    EditText etEditText;
    public String newItemStr;
    ListView lvlItems;
    TodoObject todoObject;
    String text;
    public static final String INTENT_EXTRA_OBJECT = "toDoObject";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new TodoItemsDBHelper(getApplicationContext());
        populateArrayItems();

        etEditText = (EditText) findViewById(R.id.addText);
        lvlItems = (ListView) findViewById(R.id.list_story_view);
        lvlItems.setAdapter(customItemsadapter);
        lvlItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoObject td = (TodoObject) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MainActivity.this, EditTodoItemActivity.class);
                intent.putExtra(INTENT_EXTRA_OBJECT, td);
                startActivity(intent);
            }
        });
        db.closeDB();
    }


    public void populateArrayItems()
    {
        readItems();
        customItemsadapter = new TodoItemsAdapter(this, R.layout.list_view, todoItems);
    }


    public void onRestart(){
        super.onRestart();
        this.finish();
        this.startActivity(getIntent());
    }


    public void btnsaveonclick (View view)
    {
        text = etEditText.getText().toString();
        if(text.trim().equals(""))
        {
            Toast.makeText(this, "This field cannot be blank", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final AlertDialog.Builder priority = new AlertDialog.Builder(this);
            priority.setTitle("Priority");
            String [] p1 = new String[]{
                    "High",
                    "Low",
                    "Medium"
            };
            priority.setSingleChoiceItems(p1, 1, null);
            DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog alert = (AlertDialog)dialog;
                    int position = alert.getListView().getCheckedItemPosition();
                    String priorityPosition;
                    switch (position)
                    {
                        case 0 : priorityPosition =  "High";
                            break;
                        case 1 : priorityPosition = "Low";
                            break;
                        case 2 : priorityPosition = "Medium";
                            break;
                        default: priorityPosition = "";
                            break;
                    }
                    todoObject = new TodoObject(text, priorityPosition);
                    db.createTodo(todoObject);
                   onRestart();
                    etEditText.setText("");
                }
            };
            priority.setPositiveButton("Ok", positiveListener);
            priority.setNegativeButton("Cancel", null);
            priority.show();
            populateArrayItems();
        }
    }


    private void readItems() {
        Log.d("Log", "reading items from DB");
        try {
            todoItems = db.getAllTodo();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activities in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

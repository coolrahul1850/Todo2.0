package org.snake.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.snake.android.datamodels.TodoItemsDBHelper;
import org.snake.android.datamodels.TodoObject;

public class EditTodoItemActivity extends AppCompatActivity {


    TodoObject todoObject;
    TodoItemsDBHelper db;
    EditText editItemActivityEditText;
    RadioGroup groupItemPriority;
    RadioButton editRadioButton;
    int editItemPosition;
    RadioButton ItemPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        findViewsById();
        db = new TodoItemsDBHelper(getApplicationContext());

        todoObject = null;
        Intent intent = this.getIntent();
        if (intent.hasExtra(MainActivity.INTENT_EXTRA_OBJECT)) {
            todoObject = (TodoObject) intent.getSerializableExtra(MainActivity.INTENT_EXTRA_OBJECT);
            preloadViewData(todoObject);
        }
    }

    private void findViewsById() {
        editItemActivityEditText = (EditText) findViewById(R.id.editItemActivityEditText);

    }

    private void preloadViewData(TodoObject td) {
        editItemActivityEditText.setText(td.getText());
        Log.d("PVC",td.getPriority());

        switch (td.getPriority().toLowerCase().trim()){
            case "high":
                ItemPriority = (RadioButton) findViewById(R.id.radioBtnHigh);
                ItemPriority.setChecked(true);
                break;
            case "low":
                ItemPriority = (RadioButton) findViewById(R.id.radioBtnLow);
                ItemPriority.setChecked(true);
                break;
            case "medium":
                ItemPriority = (RadioButton) findViewById(R.id.radioBtnMedium);
                ItemPriority.setChecked(true);
                break;
            default:
                ItemPriority = (RadioButton) findViewById(R.id.radioBtnHigh);
                ItemPriority.setChecked(true);
                break;
        }

    }

    public void btneditItemSave (View view)
    {
        todoObject.setText(editItemActivityEditText.getText().toString());
        todoObject.setPriority("High");
        db.updateToDo(todoObject);
        Intent resultbackMainActivity = new Intent();
        setResult(RESULT_OK, resultbackMainActivity);
        this.finish();
    }

    public void deleteItem (View view)
    {
        if (todoObject!= null){
            db.deleteToDo(todoObject.getId());
        }
        Intent resultbackMainActivity = new Intent();
        setResult(RESULT_OK, resultbackMainActivity);
        this.finish();
    }
}

package org.snake.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    RadioButton itemPriority;
    RadioGroup newPriority;
    int flag;

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

        switch (td.getPriority().toLowerCase().trim()){
            case "high":
                itemPriority = (RadioButton) findViewById(R.id.radioBtnHigh);
                itemPriority.setChecked(true);
                flag =0;
                break;
            case "low":
                itemPriority = (RadioButton) findViewById(R.id.radioBtnLow);
                itemPriority.setChecked(true);
                flag =1;
                break;
            case "medium":
                itemPriority = (RadioButton) findViewById(R.id.radioBtnMedium);
                itemPriority.setChecked(true);
                flag=2;
                break;
            default:
                itemPriority = (RadioButton) findViewById(R.id.radioBtnHigh);
                itemPriority.setChecked(true);
                flag=0;
                break;
        }

    }

    public void btneditItemSave (View view)
    {
        todoObject.setText(editItemActivityEditText.getText().toString());
        newPriority = (RadioGroup) findViewById(R.id.radioBtnGroupPriority);
        int newCheckedButton = newPriority.getCheckedRadioButtonId();
        itemPriority = (RadioButton) findViewById(newCheckedButton);
        todoObject.setPriority(itemPriority.getText().toString());

        db.updateToDo(todoObject);
        this.finish();
    }

    public void deleteItem (View view)
    {
        if (todoObject!= null){
            db.deleteToDo(todoObject.getId());
        }
        this.finish();
    }
}

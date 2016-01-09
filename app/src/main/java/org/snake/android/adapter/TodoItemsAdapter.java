package org.snake.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.snake.android.activities.R;
import org.snake.android.datamodels.TodoObject;

import java.util.List;


public class TodoItemsAdapter extends ArrayAdapter<TodoObject>{

    public TodoItemsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TodoItemsAdapter(Context context, int resource, List<TodoObject> items) {
        super(context, resource, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_view, null);
        }

        TodoObject td = getItem(position);

        if (td != null) {
            TextView tvPriority = (TextView) v.findViewById(R.id.priority);
            TextView tvItemText = (TextView) v.findViewById(R.id.item_text);


            tvPriority.setText(td.getPriority().toString());
            tvItemText.setText(td.getText());

        }

        return v;
    }
}

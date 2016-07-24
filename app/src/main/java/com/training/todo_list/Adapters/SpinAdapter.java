package com.training.todo_list.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.training.todo_list.model.models.TodoType;

import java.util.List;

/**
 * Created by Nabila_Salhi on 2016-07-22.
 */
public class SpinAdapter extends ArrayAdapter<TodoType> {
    private Context context;
    private List<TodoType> values;

    public SpinAdapter(Context context, int textViewResourceId,
                       List<TodoType> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public TodoType getItem(int position){
        return  values.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).name());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).name());

        return label;
    }
}

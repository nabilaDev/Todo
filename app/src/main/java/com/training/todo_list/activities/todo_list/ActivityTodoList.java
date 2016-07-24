package com.training.todo_list.activities.todo_list;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.training.todo_list.Adapters.CustomAdapter;
import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.widget.AdapterView.*;


public class ActivityTodoList extends ListActivity {
    ListView listView;
    TodoManager todoManager;
    TodoTypeManager todoTypeManager;
    List<Todo> data ;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lt_act_todo_list);
        listView = (ListView) findViewById(android.R.id.list);
        todoManager = new TodoManager();
        todoTypeManager = new TodoTypeManager();
        data = todoManager.all();
        Resources res = getResources();


        /**************** Create Custom Adapter *********/
        adapter = new CustomAdapter(ActivityTodoList.this, (ArrayList) data, res);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Todo todo = adapter.getItem(i);
               String description =todo.description();
               UUID id =todo.id();
               Boolean isDone =todo.isDone();
               UUID idType =todo.idTodoType();
                TodoType todoType = todoTypeManager.todoTypeFor(idType);
                String nameType = todoType.name();
                Intent intent = new Intent(ActivityTodoList.this,ActivityUpdateTodo.class);
                intent.putExtra("description",description);
                intent.putExtra("id",""+id);
                intent.putExtra("isDone",isDone);
                intent.putExtra("nameType",nameType);
                startActivity(intent);
            }
        });

    }
    public void askAddTodo(View pView) {
        Toast.makeText(this, "Ask add todo", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ActivityTodoList.this,ActivityAddTodo.class);
        startActivity(intent);
    }


    public void askSurprise(View pView) {
        AlertDialog.Builder tBuilder = new AlertDialog.Builder(this);
        tBuilder.setTitle(R.string.dialog_title_surprise);
        tBuilder.setMessage(R.string.dialog_message_surprise);
        tBuilder.setPositiveButton(R.string.confirm, null);
        tBuilder.show();
    }
}

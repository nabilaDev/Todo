package com.training.todo_list.activities.todo_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.training.todo_list.Adapters.SpinAdapter;
import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.TodoType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class ActivityUpdateTodo extends AppCompatActivity {
    private Button update ;
    private Button cancel ;
    private EditText description;
    private String descriptionText;
    private TodoManager todoManager;
    DateFormat dateFormat;
    private TextView switchStatus;
    private Switch mySwitch;
    boolean isdone;
    private SpinAdapter adapter;
    private UUID idType;
    private UUID id;
    private Spinner spinner;
    List<TodoType> myTypes ;
    private String nameType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_update_todo);

        update =(Button)findViewById(R.id.btn_save);
        cancel =(Button)findViewById(R.id.btn_cancel);
        description =(EditText)findViewById(R.id.txtDescription);
        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.mySwitch);
        spinner = (Spinner) findViewById(R.id.spinner);

        TodoTypeManager todoTypeManager = new TodoTypeManager();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        todoManager = new TodoManager();
        myTypes=todoTypeManager.all();
        Intent intent = getIntent();
        descriptionText = intent.getStringExtra("description");
        String idTodo = intent.getStringExtra("id");
        Log.e("idTodo "," : "+idTodo);
        id = UUID.fromString(idTodo);
        nameType = intent.getStringExtra("nameType");
        Log.e("nameType "," : "+nameType);
        boolean isDone = intent.getBooleanExtra("isDone",false);
        Toast.makeText(ActivityUpdateTodo.this,descriptionText,Toast.LENGTH_LONG).show();

        description.setText(descriptionText);
        mySwitch.setChecked(isDone);
        if(isDone)
            switchStatus.setText("Task is done");
        else
            switchStatus.setText("Task not done yet");

        // Declaring an Adapter and initializing it to the data pump
        adapter = new SpinAdapter(ActivityUpdateTodo.this,
                android.R.layout.simple_spinner_item,
                myTypes);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Setting Adapter to the Spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(getIndex(spinner, nameType));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                TodoType todoType = adapter.getItem(position);
                idType=todoType.id();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call update function
                descriptionText = description.getText().toString();


                if (mySwitch.isChecked())
                    isdone = true;
                else
                    isdone =false;
                todoManager.update(descriptionText,idType,id,isdone);
                Intent intent = new Intent(ActivityUpdateTodo.this,ActivityTodoList.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    //private method of your class
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

}

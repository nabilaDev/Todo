package com.training.todo_list.activities.todo_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.training.todo_list.Adapters.SpinAdapter;
import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.TodoType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ActivityAddTodo extends AppCompatActivity {
    List<TodoType> myTypes ;

    private Button save ;
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
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lt_act_add_todo);
        spinner = (Spinner) findViewById(R.id.spinner);
        save =(Button)findViewById(R.id.btn_save);
        cancel =(Button)findViewById(R.id.btn_cancel);
        description =(EditText)findViewById(R.id.txtDescription);
        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.mySwitch);
        TodoTypeManager todoTypeManager = new TodoTypeManager();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        todoManager = new TodoManager();
        myTypes=todoTypeManager.all();

        //set the switch to ON
        mySwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switchStatus.setText("Task is done");
                }else{
                    switchStatus.setText("Task not done yet");
                }

            }
        });

        //check the current state before we display the screen
        if(mySwitch.isChecked()){
            switchStatus.setText("Task is done");
        }
        else {
            switchStatus.setText("Task not done yet");
        }
        // Declaring an Adapter and initializing it to the data pump
        adapter = new SpinAdapter(ActivityAddTodo.this,
                android.R.layout.simple_spinner_item,
                myTypes);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Setting Adapter to the Spinner
        spinner.setAdapter(adapter);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descriptionText = description.getText().toString();

                Date date = new Date();
                if (mySwitch.isChecked())
                    isdone = true;
                else
                    isdone =false;
                todoManager.create(descriptionText,date,idType,isdone);
                Intent intent = new Intent(ActivityAddTodo.this,ActivityTodoList.class);
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
}

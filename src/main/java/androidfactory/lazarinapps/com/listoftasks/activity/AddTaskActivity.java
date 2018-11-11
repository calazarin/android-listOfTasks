package androidfactory.lazarinapps.com.listoftasks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidfactory.lazarinapps.com.listoftasks.R;
import androidfactory.lazarinapps.com.listoftasks.helper.ITaskDAO;
import androidfactory.lazarinapps.com.listoftasks.helper.TaskDAO;
import androidfactory.lazarinapps.com.listoftasks.helper.ToastHelper;
import androidfactory.lazarinapps.com.listoftasks.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText newTaskNameInputText;

    private Task currentTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);

        newTaskNameInputText = findViewById(R.id.newTaskNameInputText);

        //retrieve selected task if we are editing
        currentTask = (Task) getIntent().getSerializableExtra(MainActivity.SELECTED_TASK);
        if(currentTask != null){
            newTaskNameInputText.setText(currentTask.getTaskName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.itemSave:
                ITaskDAO taskDAO = new TaskDAO(this.getApplicationContext());
                if(currentTask  != null){

                    if (validateTaskData()) {
                        currentTask.setTaskName(String.valueOf(newTaskNameInputText.getText()));
                        if (taskDAO.update(currentTask)) {
                            finish();
                            ToastHelper.createToastAndShowIt(AddTaskActivity.this,"Task updated with success!", Toast.LENGTH_SHORT);
                        } else {
                            ToastHelper.createToastAndShowIt(AddTaskActivity.this,"Error to update your task! Please try again", Toast.LENGTH_SHORT);
                        }
                    } else {
                        ToastHelper.createToastAndShowIt(AddTaskActivity.this,"Please add a task description!", Toast.LENGTH_SHORT);
                    }

                }else{
                    Task newTask = new Task();
                    if(validateTaskData()){
                        newTask.setTaskName(String.valueOf(newTaskNameInputText.getText()));
                        if(taskDAO.save(newTask)){
                            finish();
                            ToastHelper.createToastAndShowIt(AddTaskActivity.this,"New task added with success!", Toast.LENGTH_SHORT);
                        }else{
                            ToastHelper.createToastAndShowIt(AddTaskActivity.this,"Error to add a new task! Please try again", Toast.LENGTH_SHORT);
                        }
                    }else{
                        ToastHelper.createToastAndShowIt(AddTaskActivity.this,"Please add a task description!", Toast.LENGTH_SHORT);
                    }
                }
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateTaskData(){
        String taskName = String.valueOf(newTaskNameInputText.getText());
        return !taskName.isEmpty();
    }
}
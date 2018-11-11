package androidfactory.lazarinapps.com.listoftasks.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidfactory.lazarinapps.com.listoftasks.R;
import androidfactory.lazarinapps.com.listoftasks.adapter.TaskAdapter;
import androidfactory.lazarinapps.com.listoftasks.helper.ITaskDAO;
import androidfactory.lazarinapps.com.listoftasks.helper.RecyclerItemClickListener;
import androidfactory.lazarinapps.com.listoftasks.helper.TaskDAO;
import androidfactory.lazarinapps.com.listoftasks.helper.ToastHelper;
import androidfactory.lazarinapps.com.listoftasks.model.Task;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listOfTasksRecyclerView;

    private TaskAdapter taskAdapter;

    private List<Task> taskList = new ArrayList<>();

    public static final String SELECTED_TASK = "selectedTask";

    private Task selectedTaskForDeletion;

    private ITaskDAO taskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskDAO = new TaskDAO(getApplicationContext());

        listOfTasksRecyclerView = findViewById(R.id.recyclerTaskList);

        listOfTasksRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this.getApplicationContext(),
                        listOfTasksRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //retrieve task to edit
                                Task selectedTask = taskList.get(position);
                                // send it to next activity
                                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                                intent.putExtra(SELECTED_TASK,selectedTask);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                selectedTaskForDeletion = taskList.get(position);
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                dialog.setTitle("Confirm exclusion");
                                dialog.setMessage("Do you really want to delete the task: "+selectedTaskForDeletion.getTaskName()+"?");
                                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(taskDAO.delete(selectedTaskForDeletion)){
                                            loadTaskList();
                                            ToastHelper.createToastAndShowIt(MainActivity.this,"Task deleted successfully!",Toast.LENGTH_SHORT);
                                        }else{
                                            ToastHelper.createToastAndShowIt(MainActivity.this,"Error to delete the task!",Toast.LENGTH_SHORT);
                                        }
                                    }
                                });
                                dialog.setNegativeButton("No", null);
                                dialog.create().show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTaskList();
    }

    private void loadTaskList(){

        //list tasks
        taskList = taskDAO.listTasks();

        //creating adapter
        taskAdapter = new TaskAdapter(taskList);

        //configuring recycler view

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        listOfTasksRecyclerView.setLayoutManager(layoutManager);
        listOfTasksRecyclerView.setHasFixedSize(true);
        listOfTasksRecyclerView.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        listOfTasksRecyclerView.setAdapter(taskAdapter);

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
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}

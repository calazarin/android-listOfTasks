package androidfactory.lazarinapps.com.listoftasks.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidfactory.lazarinapps.com.listoftasks.model.Task;

/**
 *
 */
public class TaskDAO implements ITaskDAO{

    private SQLiteDatabase readDB;

    private SQLiteDatabase writeDB;

    public TaskDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        readDB = dbHelper.getReadableDatabase();
        writeDB = dbHelper.getWritableDatabase();
    }

    public boolean save(Task task){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", task.getTaskName());
            writeDB.insert(DBHelper.TASK_TABLE,null,contentValues);
            Log.i("INFO DB", "New task inserted successfully [ task name="+task.getTaskName()+" ]");
        }catch(Exception ex){
            Log.i("ERROR_DB", "Error to save a new task: "+ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Task task) {
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", task.getTaskName());
            writeDB.update(DBHelper.TASK_TABLE,contentValues,"id=?",new String[]{String.valueOf(task.getId())});
            Log.i("INFO DB", "Task updated successfully [ task name="+task.getTaskName()+" ]");
        }catch(Exception ex){
            Log.i("ERROR_DB", "Error to update a task: "+ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Task task) {
        try{
            writeDB.delete(DBHelper.TASK_TABLE,"id=?",new String[]{String.valueOf(task.getId())});
            Log.i("INFO DB", "Task deleted successfully [ task name="+task.getTaskName()+" ]");
        }catch(Exception ex){
            Log.i("ERROR_DB", "Error to delete a task: "+ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Task> listTasks() {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM "+DBHelper.TASK_TABLE+" ;";
        Cursor cursor = readDB.rawQuery(sql, null);
        while(cursor.moveToNext()){
            taskList.add(new Task(cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name"))));
        }
        return taskList;
    }

}
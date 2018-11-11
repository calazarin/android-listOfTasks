package androidfactory.lazarinapps.com.listoftasks.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    //it will change if we update this app later on
    private static int VERSION = 2;

    private static String NAME_DB = "DB_LIST_OF_TASKS";

    public static String TASK_TABLE = "tasks";

    public DBHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //called when app is installed

        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TASK_TABLE);
        sql.append(" ( id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("name TEXT NOT NULL );");

        try {
            db.execSQL(sql.toString());
            Log.i("INFO DB", "Table tasks created successfully");
        }catch(Exception ex){
            Log.i("ERROR_DB", "Error creating table: "+ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //called when user updates the app
        Log.i("DB INFO", "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(db);
    }


}
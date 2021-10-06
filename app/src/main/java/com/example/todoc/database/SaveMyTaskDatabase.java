package com.example.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoc.database.dao.TaskDao;
import com.example.todoc.model.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class SaveMyTaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    private static volatile SaveMyTaskDatabase INSTANCE;

    public static SaveMyTaskDatabase getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (SaveMyTaskDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveMyTaskDatabase.class, "SaveMyTaskDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                ContentValues contentValues = new ContentValues();
                contentValues.put("task_id", 1);
                contentValues.put("task_name", "task1");
                contentValues.put("project_id", 1);
                contentValues.put("creationTimestamp", 1);

                db.insert("Task", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}

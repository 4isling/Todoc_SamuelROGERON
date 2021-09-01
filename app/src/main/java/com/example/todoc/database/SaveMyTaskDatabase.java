package com.example.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoc.database.dao.ProjectDao;
import com.example.todoc.database.dao.TaskDao;
import com.example.todoc.model.Project;
import com.example.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveMyTaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    private static volatile SaveMyTaskDatabase INSTANCE;

    public static SaveMyTaskDatabase getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (SaveMyTaskDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveMyTaskDatabase.class, "TheDataBase.db")
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
                contentValues.put("task_id",1);
                contentValues.put("name", "Vitres IBM");
                contentValues.put("project_id", 1L);

                db.insert("Task", OnConflictStrategy.IGNORE, contentValues);

                super.onCreate(db);
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("project_id", 1);
                contentValues1.put("project_name", "Projet Tartampion");
                contentValues1.put("color", 0xFFEADAD1);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues1);

                super.onCreate(db);
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("project_id", 2);
                contentValues2.put("project_name", "Projet Lucidia");
                contentValues2.put("color", 0xFFB4CDBA);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues2);

                super.onCreate(db);
                ContentValues contentValues3 =  new ContentValues();
                contentValues3.put("project_id", 3);
                contentValues3.put("project_name", "Projet Circus");
                contentValues3.put("color", 0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues3);
            }
        };

    }
}

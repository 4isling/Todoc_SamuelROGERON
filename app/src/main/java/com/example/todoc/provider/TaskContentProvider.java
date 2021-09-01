package com.example.todoc.provider;

import android.content.ContentUris;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoc.database.SaveMyTaskDatabase;
import com.example.todoc.model.Task;

import static java.security.AccessController.getContext;

public class TaskContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.openclassrooms.todoc.provider";
    public static final String TABLE_NAME = Task.class.getSimpleName();
    public static final Uri URI_ITEM = Uri.parse("content://" + AUTHORITY + "/"+ TABLE_NAME);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if(getContext() == null){
            long task_id = ContentUris.parseId(uri);
            final Cursor cursor = SaveMyTaskDatabase.getInstance(getContext()).taskDao().getTaskWithCursor(task_id);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        throw new IllegalArgumentException("failed to query row for uri" + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vns.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if(getContext() != null){
            final long id = SaveMyTaskDatabase.getInstance(getContext()).taskDao().insertTask(Task.fromContentValues(contentValues));
            if(id != 0){
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri,id);
            }
        }
        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if(getContext() != null){
        final int count = SaveMyTaskDatabase.getInstance(getContext()).taskDao().deleteTask(ContentUris.parseId(uri));
        getContext().getContentResolver().notifyChange(uri, null);
        }
        throw new IllegalArgumentException("Failed to delete row into " +  uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (getContext()!= null){
            final int count = SaveMyTaskDatabase.getInstance(getContext()).taskDao().updateTask(Task.fromContentValues(values));
            getContext().getContentResolver().notifyChange(uri,null);
            return count;
        }
        throw new IllegalArgumentException("Failed to update row into " + uri);
    }


}

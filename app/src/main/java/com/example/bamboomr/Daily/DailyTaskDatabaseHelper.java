package com.example.bamboomr.Daily;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DailyTaskDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATEBASE_NAME = "DailyTask.db";

    public static final String CREATE_TABLE_TASK = "create table task ("
            +"date text," //日期 几号
            +"time text," //时间 几点
            +"name text,"
            +"id text,"
            +"cycle real,"
            +"duration real,"
            +"record_duration real)";

    private Context context;

    public DailyTaskDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK);//创建表
        Toast.makeText(context, "Create succeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

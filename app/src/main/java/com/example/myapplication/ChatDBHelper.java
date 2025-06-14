package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ChatMessages.db";
    public static final int DB_VERSION = 1;

    public ChatDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE chat (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "groupName TEXT, " +
                "sender TEXT, " +
                "message TEXT, " +
                "time TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS chat");
        onCreate(db);
    }
}

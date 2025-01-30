package com.example.sqlapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyOpenHelper extends SQLiteOpenHelper {


    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "sqlapp";

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE comment ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, body TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addComment(Comment comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", comment.getName());
        contentValues.put("body", comment.getBody());
        db.insert("comment", null, contentValues);
        db.close();
    }

    public ArrayList<Comment> getComments(){
        ArrayList<Comment> commentArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM comment", null);
        if (cursor.getCount() != 0){
            if (cursor.moveToFirst()){
                do {
                    Comment comment = new Comment();

                    comment.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    comment.setBody(cursor.getString(cursor.getColumnIndexOrThrow("body")));

                    commentArrayList.add(comment);

                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return commentArrayList;
    }

    public void removeComment(Comment comment) {
        try {
            String[] args = {comment.getName(), comment.getBody() };
             SQLiteDatabase db = this.getWritableDatabase();
             db.execSQL("delete from comment where name=? and body=?", args);
             db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

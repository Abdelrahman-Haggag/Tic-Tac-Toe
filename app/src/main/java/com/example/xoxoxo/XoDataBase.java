package com.example.xoxoxo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class XoDataBase extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "xoxoxo_db";
    static final String TABLE_PERSON = "user";

    public XoDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_PERSON +"(email Text unique , password Text unique)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ TABLE_PERSON);
        onCreate(db);
    }

    public boolean insertUser(User user){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("email",user.getEmail());
        values.put("password",user.getPassword());
        long res=db.insert(TABLE_PERSON,null,values);
        return res!=-1;
    }

    public boolean updateUser(User user){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("email",user.getEmail());
        values.put("password",user.getPassword());
        String arg[]={ user.getEmail(), user.getPassword()};
        long res=db.update(TABLE_PERSON,values,"email=? and password=?",arg);
        return res!=-1;
    }

    public boolean deleteData(String email){
        SQLiteDatabase db=getWritableDatabase();
        int delete=db.delete(TABLE_PERSON,"email = ?",new String[]{email});
        return delete!=-1;
    }

    public ArrayList<User> retrieveAllData(){
        ArrayList<User>users=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+ TABLE_PERSON,null);

        if(c!=null&&c.moveToFirst()){
            do{
                String email=c.getString(0);
                String password=c.getString(1);
                users.add(new User(email,password));
            }while(c.moveToNext());
        }
        c.close();
        return users;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PERSON + " WHERE email =? AND password =?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists; // Returns true if user exists
    }
}


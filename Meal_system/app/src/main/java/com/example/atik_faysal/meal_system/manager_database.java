package com.example.atik_faysal.meal_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLException;

public class manager_database extends SQLiteOpenHelper {
    private static final String table_name = "man_info";
    private static final String database_name = "ManagerInformation";
    private static final int database_version = 1;
    private static final String u_username = "u_username";
    private static final String u_name = "u_name";
    private static final String u_email = "u_email";
    private static final String u_phone = "u_phone";
    private static final String u_address = "u_address";
    private static final String u_password = "u_password";
    public SQLiteDatabase db = this.getWritableDatabase();
    public manager_database(Context context) {
        super(context, database_name, null, database_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table "+table_name+" ("+u_username+" Text,"+u_name+" Text,"+u_phone+" Text,"+u_email+" Text,"+u_address+" Text,"+u_password+" Text)";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void add_manager_information(String username,String name,String phone,String email,String address,String password)
    {
       try
       {
           ContentValues values = new ContentValues();
           values.put(u_username,username);
           values.put(u_name,name);
           values.put(u_phone,phone);
           values.put(u_email,email);
           values.put(u_address,address);
           values.put(u_password,password);
           db.insert(table_name,null,values);
           db.close();

       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
    }



    public Cursor log_in(String username)
    {
        String sql = "SELECT * FROM "+table_name+" where "+u_username+"='"+username+"'";
        Cursor cursor;
        cursor = db.rawQuery(sql,null);
        return  cursor;
    }


    public Cursor get_all_name()
    {
        String sql = "SELECT `u_username` FROM "+table_name;
        Cursor value ;
        value = db.rawQuery(sql,null);
        return value;
    }


    public String UpdateInformation(String username,String phone,String email,String address)
    {
        ContentValues values = new ContentValues();
        values.put(u_phone,phone);
        values.put(u_email,email);
        values.put(u_address,address);
        db.update(table_name,values,u_username+"=?",new String[] {username});
        return "Manager Information Update";
    }

    public String changePassword(String username,String oldPass,String newPsas)
    {
        Cursor value;
        String sql = "SELECT `u_password` FROM "+table_name+" WHERE u_username ='"+username+"'";
        String save_password;
        value = db.rawQuery(sql,null);
        value.moveToNext();
        save_password = value.getString(0);

        if(save_password.equals(oldPass))
        {
            ContentValues values = new ContentValues();
            values.put(u_password,newPsas);
            db.update(table_name,values,u_username+"=?",new String[] {username});

            return "Password Changed";
        }else return "Error";
    }

    public Cursor GetNamePhone(String username)
    {
        String sql = "SELECT `u_name`,`u_phone` FROM "+table_name+" WHERE u_username='"+username+"'";
        Cursor value;
        value = db.rawQuery(sql,null);
        return value;
    }
}

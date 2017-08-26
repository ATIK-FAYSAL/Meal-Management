package com.example.atik_faysal.meal_system;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.SQLException;
import java.util.ArrayList;

public class bazar_database extends SQLiteOpenHelper{

    private static final String database_name = "bazar_database";
    private static final int database_version = 1;
    private static final String table_name = "bazar_table";
    private static final String date = "date";
    private static final String bazar = "bazar";
    private static final String m_name = "m_name";
    public SQLiteDatabase db = this.getWritableDatabase();
    public bazar_database(Context context)
    {
        super(context,database_name,null,database_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table "+table_name+" ("+date+" Varchar(20),"+bazar+" INTEGER not null,"+m_name+"  Varchar(25))";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String save_bazar_cost(String Date,int taka,String name)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(date,Date);
            values.put(bazar,taka);
            values.put(m_name,name);
            db.insert(table_name,null,values);
        }catch (android.database.SQLException e)
        {
            e.printStackTrace();
        }
        return "value inserted";
    }

    public Cursor GetAllBazarList()
    {
        String sql = "SELECT * FROM "+table_name;
        Cursor value;
        value = db.rawQuery(sql,null);
        return value;
    }
    public int sum_bazar()
    {
        Cursor value = db.rawQuery("SELECT SUM(bazar) FROM " + table_name, null);
        value.moveToFirst();
        int sum = value.getInt(0);
        value.close();
        return sum;
    }

    public Cursor GetDate()
    {
    String sql = "SELECT `date` FROM "+table_name;
        Cursor value;
        value = db.rawQuery(sql,null);
        return value;
    }

    public void UpdateCost(String Date,int taka,String name)
    {
        ContentValues values = new ContentValues();
        values.put(bazar,taka);
        values.put(m_name,name);
        db.update(table_name,values,date+"=?",new String[]{Date});
    }
    public void deleteReport()
    {
        db.execSQL("delete from "+ table_name);
    }
}

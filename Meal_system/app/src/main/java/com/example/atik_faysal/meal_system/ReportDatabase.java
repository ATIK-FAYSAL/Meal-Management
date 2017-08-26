package com.example.atik_faysal.meal_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by user on 8/22/2017.
 */

public class ReportDatabase  extends SQLiteOpenHelper
{

    private static final String table_name = "Report";
    private static final String database_name = "MonthlyReport";
    private static final int database_version = 1;
    private static final String date = "date";
    private static final String m_name = "m_name";
    private static final String m_meal = "m_meal";
    private static final String m_taka = "m_taka";
    private static final String m_cost = "m_cost";
    private static final String m_due = "m_due";
    private static final String table = "MealRateTable";
    private static final String total_taka = "total_taka";
    private static final String total_cost = "total_cost";
    private static final String mealRate = "mealRate";
    private static final String total_meal = "total_meal";
    public SQLiteDatabase db = this.getWritableDatabase();

    public ReportDatabase(Context context)
    {
        super(context,database_name,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table "+table_name+" ("+date+" Text,"+m_name+" Text,"+m_meal+" Text,"+m_taka+" Text,"+m_cost+" Text,"+m_due+" Text)";
        db.execSQL(create_table);
        String sql = "create table "+table+" ("+date+" Varchar(15),"+total_taka+" varchar(20),"+total_cost+" Varchar(20),"+total_meal+" Varchar(10),"+mealRate+" Varchar(10))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertReport(String Date,String name,String meal,String taka,String cost,String due)
    {
        ContentValues values = new ContentValues();
        values.put(date,Date);
        values.put(m_name,name);
        values.put(m_meal,meal);
        values.put(m_taka,taka);
        values.put(m_cost,cost);
        values.put(m_due,due);
        db.insert(table_name,null,values);
    }
    public Cursor CheckDate()
    {
        Cursor value ;
        String sql = "SELECT `date` FROM "+table_name;
        value = db.rawQuery(sql,null);
        return value;
    }
    public void saveMealRate(String Date,String taka,String cost,String meal,String mealrate)
    {
        ContentValues values = new ContentValues();
        values.put(date,Date);
        values.put(total_taka,taka);
        values.put(total_cost,cost);
        values.put(total_meal,meal);
        values.put(mealRate,mealrate);
        db.insert(table,null,values);
    }

    public Cursor GetReport(String Date)
    {
        Cursor value;
        String sql = "SELECT * FROM "+table_name+" WHERE date='"+Date+"'";
        value = db.rawQuery(sql,null);
        return value;
    }
    public Cursor GetMealInformation(String Date)
    {
        Cursor value;
        String sql = "SELECT * FROM "+table+" WHERE date='"+Date+"'";
        value = db.rawQuery(sql,null);
        return value;
    }

    public boolean ifDateExist(String date)
    {
        Cursor value;
        ArrayList<String>date_list = new ArrayList<>();
        String sql = "SELECT `date` FROM "+table_name;
        value = db.rawQuery(sql,null);
        while(value.moveToNext())date_list.add(value.getString(0));
        if(date_list.contains(date))return true;
        else return false;
    }

    public void deleteReport()
    {
        db.execSQL("delete from "+ table_name);
    }
    public void deleteAll()
    {
        db.execSQL("delete from "+ table);
    }
}

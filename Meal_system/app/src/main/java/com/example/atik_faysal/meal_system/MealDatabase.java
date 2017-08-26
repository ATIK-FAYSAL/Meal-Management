package com.example.atik_faysal.meal_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MealDatabase extends SQLiteOpenHelper {

    private static final String database_name = "meal_database";
    private static final int database_version = 1;
    private static final String m_name = "m_name";
    private static final String breakfast = "breakfast";
    private static final String launch = "launch";
    private static final String dinner = "dinner";
    private static final String date = "date";
    private static final String table_name = "mealTable";
    public SQLiteDatabase db = this.getWritableDatabase();

    public MealDatabase(Context context)
    {
        super(context,database_name,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table "+table_name+" ("+date+" Varchar(20),"+m_name+" Varchar(25),"+breakfast+" Integer(2),"+launch+" Integer(2),"+dinner+" Integer(2))";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String SaveMeal(String Date,String Name,int Breakfast,int Launch,int Dinner)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(date,Date);
            values.put(m_name,Name);
            values.put(breakfast,Breakfast);
            values.put(launch,Launch);
            values.put(dinner,Dinner);
            db.insert(table_name,null,values);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "Meal added for "+Name;
    }

    public Cursor GetAllInformation()
    {
        Cursor value;
        String sql = "SELECT * FROM "+table_name;
        value = db.rawQuery(sql,null);
        return value;
    }

    public Cursor GetNameDate(String Date)
    {
        Cursor value;
        String sql = "SELECT m_name FROM "+table_name+" WHERE "+date+"='"+Date+"'";
        value = db.rawQuery(sql,null);
        return value;
    }


    public Cursor GetPersonMeal(String name)
    {
        Cursor value;
        String sql = "SELECT * FROM "+table_name+" WHERE "+m_name+"='"+name+"'";
        value = db.rawQuery(sql,null);
        return value;
    }


    public String UpdatePersonMeal(String Date,String name,int b,int l,int d)
    {
        ContentValues value = new ContentValues();
        value.put(breakfast,b);
        value.put(launch,l);
        value.put(dinner,d);
        db.update(table_name,value,date+"=? and "+m_name+"=?",new String[]{Date,name});
        return  "Meal Update for "+name;
    }

    public Cursor GetMealbyDate(String Date)
    {
        Cursor value;
        String sql = "SELECT * FROM "+table_name+" WHERE date ='"+Date+"'";
        value = db.rawQuery(sql,null);
        return value;
    }
    public ArrayList<Integer> TotalMeal(String Date)
    {
        ArrayList<Integer> Meal = new ArrayList<>();
        int totalBreakfast,totalLunch,totalDinner;
        Cursor value,value1,value2;
        value = db.rawQuery("SELECT SUM(breakfast) FROM " + table_name+" WHERE date='"+Date+"'", null);
        value1 = db.rawQuery("SELECT SUM(launch) FROM " + table_name+" WHERE date='"+Date+"'", null);
        value2 = db.rawQuery("SELECT SUM(dinner) FROM " + table_name+" WHERE date='"+Date+"'", null);
        value.moveToFirst();
        totalBreakfast = value.getInt(0);
        value.close();
        value1.moveToFirst();
        totalLunch = value1.getInt(0);
        value1.close();
        value2.moveToFirst();
        totalDinner = value2.getInt(0);
        value2.close();
        Meal.add(totalBreakfast);
        Meal.add(totalLunch);
        Meal.add(totalDinner);
        return Meal;
    }

    public int OneMonthMeal()
    {
        int TotalMeal;
        ArrayList<Integer> Meal = new ArrayList<>();
        int totalBreakfast,totalLunch,totalDinner;
        Cursor value,value1,value2;
        value = db.rawQuery("SELECT SUM(breakfast) FROM " + table_name, null);
        value1 = db.rawQuery("SELECT SUM(launch) FROM " + table_name, null);
        value2 = db.rawQuery("SELECT SUM(dinner) FROM " + table_name, null);
        value.moveToFirst();
        totalBreakfast = value.getInt(0);
        value.close();
        value1.moveToFirst();
        totalLunch = value1.getInt(0);
        value1.close();
        value2.moveToFirst();
        totalDinner = value2.getInt(0);
        value2.close();
        TotalMeal = totalBreakfast+totalLunch+totalDinner;
        return TotalMeal;
    }

    public int GetAllPersonalMeal(String name)
    {
        int TotalMeal;
        int totalBreakfast,totalLunch,totalDinner;
        Cursor value,value1,value2;
        value = db.rawQuery("SELECT SUM(breakfast) FROM " + table_name+" WHERE m_name='"+name+"'",null);
        value1 = db.rawQuery("SELECT SUM(launch) FROM " + table_name+" WHERE m_name='"+name+"'",null);
        value2 = db.rawQuery("SELECT SUM(dinner) FROM " + table_name+" WHERE m_name='"+name+"'",null);
        value.moveToFirst();
        totalBreakfast = value.getInt(0);
        value.close();
        value1.moveToFirst();
        totalLunch = value1.getInt(0);
        value1.close();
        value2.moveToFirst();
        totalDinner = value2.getInt(0);
        value2.close();
        TotalMeal = totalBreakfast+totalLunch+totalDinner;
        return TotalMeal;
    }

    public void deleteAll()
    {
        db.execSQL("delete from "+ table_name);
    }
}


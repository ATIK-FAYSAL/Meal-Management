package com.example.atik_faysal.meal_system;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PersonalMeal extends AppCompatActivity
{
    Toolbar toolbar;
    static String name;
    ListView list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_meal);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        list = (ListView)findViewById(R.id.list);
        PersonMealListView();
    }

    public static void get_name(String str)
    {
        name = str;
    }


    private void PersonMealListView()
    {
        Cursor value;
        MealDatabase mealDatabase = new MealDatabase(this);
        ArrayList<String>information = new ArrayList<>();
        value = mealDatabase.GetPersonMeal(name);
        while(value.moveToNext())information.add(value.getString(0)+"       "+value.getString(1)+"         "+value.getString(2)+"      "+value.getString(3)+"      "+value.getString(4));
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,R.layout.bazar_list_textview,information);
        list.setAdapter(adapter);
    }
}

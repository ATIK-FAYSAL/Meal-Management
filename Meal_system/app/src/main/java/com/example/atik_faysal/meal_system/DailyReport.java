package com.example.atik_faysal.meal_system;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class DailyReport extends AppCompatActivity
{
    public BazarMyAdapter adapter;
    public List<BazarListClass>MemberMeal;
    ListView list;
    MealDatabase meal;
    Toolbar toolbar;
    TextView date_txt,t_breakfast,t_lunch,t_dinner,t_meal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_report);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        meal = new MealDatabase(this);
        date_txt = (TextView)findViewById(R.id.date_txt);
        t_breakfast = (TextView)findViewById(R.id.t_breakfast);
        t_lunch = (TextView)findViewById(R.id.t_lunch);
        t_dinner = (TextView)findViewById(R.id.t_dinner);
        list = (ListView)findViewById(R.id.list);
        t_meal = (TextView)findViewById(R.id.t_meal);
        get_date();
        setInfoListView();
    }
    private void get_date()
    {
        final Calendar cal = Calendar.getInstance();
        date_txt.setText(new StringBuilder()
                .append(cal.get(Calendar.YEAR)).append("-").append(cal.get(Calendar.MONTH)+ 1).append("-")
                .append(cal.get(Calendar.DAY_OF_MONTH)));
    }

    private void setInfoListView()
    {
        ArrayList<Integer> Meal = new ArrayList<>();
        String date = date_txt.getText().toString();
        Cursor value;
        value = meal.GetMealbyDate(date);
        MemberMeal = new ArrayList<>();
        while(value.moveToNext())
        {
            String date_,name;
            int d,l,b;
            date_ = value.getString(0);
            name = value.getString(1);
            b = value.getInt(2);
            l = value.getInt(3);
            d = value.getInt(4);
            int sum = b+l+d;
            MemberMeal.add(new BazarListClass(date_,name,sum+""));
        }
        adapter = new BazarMyAdapter(this,MemberMeal);
        list.setAdapter(adapter);
        Meal = meal.TotalMeal(date);
        t_dinner.setText(Meal.get(2)+"");
        t_breakfast.setText(Meal.get(0)+"");
        t_lunch.setText(Meal.get(1)+"");
        t_meal.setText((Meal.get(0)+Meal.get(1)+Meal.get(2))+"");
    }
}

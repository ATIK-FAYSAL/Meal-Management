package com.example.atik_faysal.meal_system;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ShowReport extends AppCompatActivity
{
    static String txt_month,txt_year;
    List<ReportClass> member_report;
    ReportMyAdapter Adapter;
    ListView list;
    TextView txt_totalTaka,txt_totalBazar,txt_totalMeal,txt_mealRate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_report);
        txt_totalTaka = (TextView)findViewById(R.id.txt_totalTaka);
        txt_totalBazar = (TextView)findViewById(R.id.txt_totalBazar);
        txt_totalMeal = (TextView)findViewById(R.id.txt_totalMeal);
        txt_mealRate = (TextView)findViewById(R.id.txt_mealRate);
        GetMealInformation();
        GetReport();
    }

    public static void getMonthYear(String month,String year)
    {
        txt_month = month;
        txt_year = year;
    }

    private void GetReport()
    {
        member_report = new ArrayList<>();
        Cursor value;
        ReportDatabase reportDatabase = new ReportDatabase(this);
        boolean flag = reportDatabase.ifDateExist(txt_month+"-"+txt_year);
        if(flag==true)
        {
            value = reportDatabase.GetReport(txt_month+"-"+txt_year);
            while(value.moveToNext())
            {
                String name,totalTaka,totalCost,totalDue,totalMeal;
                name = value.getString(1);
                totalMeal = value.getString(2);
                totalTaka = value.getString(3);
                totalCost = value.getString(4);
                totalDue = value.getString(5);
                member_report.add(new ReportClass(name,totalMeal,totalTaka,totalCost,totalDue));
            }
            Adapter = new ReportMyAdapter(this,member_report);
            list = (ListView)findViewById(R.id.list);
            list.setAdapter(Adapter);
        }else Toast.makeText(this,"No Report Found",Toast.LENGTH_SHORT).show();

    }
    private void GetMealInformation()
    {
        ArrayList<String>meal_info = new ArrayList<>();
        Cursor value;
        ReportDatabase reportDatabase = new ReportDatabase(this);
        boolean flag = reportDatabase.ifDateExist(txt_month+"-"+txt_year);
        if(flag==true)
        {
            value = reportDatabase.GetMealInformation(txt_month+"-"+txt_year);
            while(value.moveToNext())
            {
                meal_info.add(value.getString(1));
                meal_info.add(value.getString(2));
                meal_info.add(value.getString(3));
                meal_info.add(value.getString(4));
            }
            txt_totalTaka.setText(meal_info.get(0)+"/=");
            txt_totalBazar.setText(meal_info.get(1)+"/=");
            txt_totalMeal.setText(meal_info.get(2));
            txt_mealRate.setText(meal_info.get(3)+"/=");
        }else Toast.makeText(this,"No Report Found",Toast.LENGTH_SHORT).show();
    }
}

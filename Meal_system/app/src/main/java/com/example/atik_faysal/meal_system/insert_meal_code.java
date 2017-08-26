package com.example.atik_faysal.meal_system;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class insert_meal_code extends AppCompatActivity
{
    Spinner spin_name,spin_breakfast,spin_launce,spin_dinner;
    ArrayList<String>member_name = new ArrayList<>();
    Toolbar toolbar;
    public String selected_name;
    public int BreakFastMeal,LaunceMeal,DinnerMeal;
    TextView date_txt;
    ListView list;
    public MyAdapter adapter;
    public List<MemeberMealClass>memberMeal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_meal);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spin_name = (Spinner)findViewById(R.id.spin_select_name);
        spin_breakfast = (Spinner)findViewById(R.id.spin_breakfast);
        spin_dinner = (Spinner)findViewById(R.id.spin_dinner);
        spin_launce = (Spinner)findViewById(R.id.spin_launce);
        date_txt = (TextView)findViewById(R.id.date_txt);
        set_name_in_spinner();
        get_date();
        InsertMeal();
        GetAllInformation();
    }


    private void GetAllInformation()
    {
        memberMeal = new ArrayList<>();
        MealDatabase meal = new MealDatabase(this);
        Cursor value;
        String date,name,b,l,d;
        value = meal.GetAllInformation();
        while(value.moveToNext())
        {
            date = value.getString(0);
            name = value.getString(1);
            b = value.getString(2);
            l = value.getString(3);
            d = value.getString(4);
            memberMeal.add(new MemeberMealClass(date,name,b,l,d));
        }
        adapter = new MyAdapter(this,memberMeal);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
    }
    private void get_date()
    {
        final Calendar cal = Calendar.getInstance();
        date_txt.setText(new StringBuilder()
                .append(cal.get(Calendar.YEAR)).append("-").append(cal.get(Calendar.MONTH)+ 1).append("-")
                .append(cal.get(Calendar.DAY_OF_MONTH)));
    }

    private void set_name_in_spinner()
    {
        Cursor value;
        member_database member = new member_database(this);
        value = member.GetAllMemberName();
        member_name.add("  Select Name");
        while(value.moveToNext())member_name.add("  "+value.getString(0));
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,R.layout.show_name_text,R.id.txt,member_name);
        spin_name.setAdapter(adapter);
        spin_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_name = "none";
            }
        });
    }

    private void InsertMeal()
    {
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.meal,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_breakfast.setAdapter(adapter);
        spin_launce.setAdapter(adapter);
        spin_dinner.setAdapter(adapter);
        spin_breakfast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                BreakFastMeal = Integer.parseInt(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_launce.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                LaunceMeal = Integer.parseInt(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_dinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                DinnerMeal = Integer.parseInt(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void save_data(View v)
    {
        MealDatabase meal = new MealDatabase(this);
        String Name="";
        String date = date_txt.getText().toString();
        boolean flag;
        if(selected_name.equals("  Select Name"))
        {
            setAlertDialog();
        }
        else
        {
            for(int i=2;i<selected_name.length();i++)Name+=selected_name.charAt(i);
             flag = check_name_date(Name,date);
            if(flag==true)
            {
                String str = meal.SaveMeal(date,Name,BreakFastMeal,LaunceMeal,DinnerMeal);
                Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
                GetAllInformation();
            }
            else AlertDialog();
        }
    }



    private void setAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(insert_meal_code.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please Select a Name !!");
        alertDialog.setPositiveButton("Ok",null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    private boolean check_name_date(String name,String date)
    {
        Vector<String>member_name = new Vector<>();
        MealDatabase meal = new MealDatabase(this);
        Cursor value;
        value = meal.GetNameDate(date);
        while(value.moveToNext())member_name.add(value.getString(0));
        if(member_name.contains(name))return false;
        else return true;
    }



    private void AlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(insert_meal_code.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("This Name's Meal is already inserted !!");
        alertDialog.setPositiveButton("Ok",null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }


}

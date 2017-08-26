package com.example.atik_faysal.meal_system;


import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdatePersonMeal extends AppCompatActivity
{
    ListView list;
    Toolbar toolbar;
    public static  String name=null;
    TextView date_txt,txt_name;
    EditText t_breakfast,t_launch,t_dinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_person_meal);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        date_txt = (TextView)findViewById(R.id.t_date);
        txt_name = (TextView)findViewById(R.id.t_member_name);
        t_breakfast = (EditText)findViewById(R.id.t_breakfast);
        t_launch = (EditText)findViewById(R.id.t_launch);
        t_dinner = (EditText)findViewById(R.id.t_dinner);
        GetAllInformation();
    }
    public static void get_name(String Name)
    {
        name = Name;
    }
    private void GetAllInformation()
    {
        MealDatabase meal = new MealDatabase(this);
        final ArrayList<String>date_array = new ArrayList<>();
        final ArrayList<String>name_array = new ArrayList<>();
        final ArrayList<String>breakfast_array = new ArrayList<>();
        final ArrayList<String>launch_array = new ArrayList<>();
        final ArrayList<String>dinner_array = new ArrayList<>();
        ArrayList<String>AllInformation = new ArrayList<>();
        Cursor value;
        value = meal.GetPersonMeal(name);
        while(value.moveToNext())
        {
            String date,name,b,l,d;
            date = value.getString(0);
            name = value.getString(1);
            b = value.getString(2);
            l = value.getString(3);
            d = value.getString(4);
            date_array.add(date);
            name_array.add(name);
            breakfast_array.add(b);
            launch_array.add(l);
            dinner_array.add(d);
            AllInformation.add(" "+date+"      "+name+"            "+b+"    "+l+"    "+d);
        }
        list = (ListView)findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.bazar_list_textview,AllInformation);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                date_txt.setText(date_array.get(position));
                txt_name.setText(name_array.get(position));
                t_breakfast.setText(breakfast_array.get(position));
                t_launch.setText(launch_array.get(position));
                t_dinner.setText(dinner_array.get(position));
            }
        });
    }


    public void OnClickMethod(View v)
    {
        UpdateMeal();
    }

    private  void UpdateMeal()
    {
        String name,breakfast,launch,dinner,date;
        date = date_txt.getText().toString();
        name = txt_name.getText().toString();
        breakfast = t_breakfast.getText().toString();
        launch = t_launch.getText().toString();
        dinner = t_dinner.getText().toString();
        int b,l,d;
        if(name.isEmpty())
        {
            SetWarning();
        }else
        {
            try
            {
                b = Integer.parseInt(breakfast);
                l = Integer.parseInt(launch);
                d = Integer.parseInt(dinner);
                MealDatabase meal = new MealDatabase(this);
                String str = meal.UpdatePersonMeal(date,name,b,l,d);
                GetAllInformation();
                Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
            }catch (NumberFormatException e)
            {
                setAlertDialog();
            }
        }
    }


    private void setAlertDialog()
    {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Error");
        alertdialog.setMessage("Please enter Meal !!");
        alertdialog.setPositiveButton("ok",null);
        AlertDialog builder = alertdialog.create();
        builder.show();
    }

    private void SetWarning()
    {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Warning");
        alertdialog.setMessage("Please Select a row !!");
        alertdialog.setPositiveButton("ok",null);
        AlertDialog builder = alertdialog.create();
        builder.show();
    }
}

package com.example.atik_faysal.meal_system;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class bazar_code extends AppCompatActivity
{
    Toolbar toolbar;  TextView date_text,total_taka_text,bazar_text;  EditText bazar;Spinner spin;
    String date,m_name;
    ListView list;
    public int total_cost,member_taka;
    List<BazarListClass>bazarList;
    public BazarMyAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bazar_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        date_text = (TextView) findViewById(R.id.date_text);
        bazar = (EditText) findViewById(R.id.bazar);
        bazar_text = (TextView)findViewById(R.id.bazar_text);
        total_taka_text = (TextView)findViewById(R.id.total_taka_text);
        get_date();
        set_name_spinner();
        set_all_bazar_list();
        set_total_bazar_total_taka();
    }

    private void get_date()
    {
        final Calendar cal = Calendar.getInstance();
        date_text.setText(new StringBuilder()
                .append(cal.get(Calendar.YEAR)).append("-").append(cal.get(Calendar.MONTH)+ 1).append("-")
                .append(cal.get(Calendar.DAY_OF_MONTH)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.manager_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id;
        res_id = item.getItemId();
        if(res_id==R.id.save)
        {
            save_todays_bazar();
            set_total_bazar_total_taka();
            set_all_bazar_list();
        }
        return true;
    }
    private  void set_total_bazar_total_taka()
    {
        bazar_database database = new bazar_database(this);
        member_database member = new member_database(this);
        total_cost = database.sum_bazar();
        member_taka = member.sum_of_member_taka();
        String taka = total_cost+"";
        String Taka = member_taka+"/=";
        total_taka_text.setText("  "+Taka);
        bazar_text.setText("  "+taka+"/=");
    }
    private void set_name_spinner()
    {
        member_database member = new member_database(this);
        Cursor value;
        value = member.GetAllMemberName();
        ArrayList<String> name_array = new ArrayList<>();
        name_array.add("   Select Name");
        while(value.moveToNext())name_array.add("   "+value.getString(0));
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.show_name_text,R.id.txt,name_array);
        spin = (Spinner)findViewById(R.id.name_spinner);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_name = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void save_todays_bazar()
    {
        bazar_database bazar_db = new bazar_database(this);
        String str;
        date = date_text.getText().toString();
        String cost = bazar.getText().toString();
        boolean flag = checkDate(date);
        int taka=0;
        try
        {
            taka = Integer.parseInt(cost);
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        if(m_name.equals("   Select Name"))
        {
            SetAlertDialog();
        }else {
            String name ="";
            for(int i=3;i<m_name.length();i++)name+=m_name.charAt(i);
            if((taka+total_cost)>member_taka)SetWarning();
            else
            {
                if(flag==false)
                {
                    bazar_db.UpdateCost(date,taka,name);
                    Toast.makeText(this,"Value Inserted",Toast.LENGTH_LONG).show();
                }else
                {
                    str = bazar_db.save_bazar_cost(date,taka,name);
                    Toast.makeText(this,str,Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private boolean checkDate(String date)
    {
        bazar_database bazar = new bazar_database(this);
        ArrayList<String>dateList = new ArrayList<>();
        Cursor value;
        value = bazar.GetDate();
        while(value.moveToNext())dateList.add(value.getString(0));
        if(dateList.contains(date))return false;
        else return true;
    }

    private void set_all_bazar_list() {
        final bazar_database bd = new bazar_database(this);
        Cursor value;
        value = bd.GetAllBazarList();
        bazarList = new ArrayList<>();
        while (value.moveToNext()) {
            String name,date,taka;
            taka = value.getString(1);
            date = value.getString(0);
            name = value.getString(2);
            bazarList.add(new BazarListClass(date,name,taka));
        }
        adapter = new BazarMyAdapter(this,bazarList);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }

    private void SetWarning()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(bazar_code.this);
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("You have insufficient Balance !!");
        alertDialog.setPositiveButton("ok",null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void SetAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(bazar_code.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please Select a name!!");
        alertDialog.setPositiveButton("ok",null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void setAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(bazar_code.this);
        alertDialog.setTitle("Message");
        alertDialog.setMessage("Today's Cost is already save!!\nNow You can only Update your Cost");
        alertDialog.setPositiveButton("ok",null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
}

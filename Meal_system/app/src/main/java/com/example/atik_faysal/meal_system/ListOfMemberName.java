package com.example.atik_faysal.meal_system;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ListOfMemberName extends AppCompatActivity
{
    ListView list;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_member);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        set_value_in_list();
    }
    private void set_value_in_list()
    {
        member_database member = new member_database(this);
        Cursor value = member.GetAllMemberName();
        ArrayList<String> person_name = new ArrayList<>();
        while(value.moveToNext())person_name.add(value.getString(0));
        if(person_name.isEmpty())person_name.add("   No Data Found");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.all_member_textview,person_name);
        list = (ListView)findViewById(R.id.list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String)list.getItemAtPosition(position);
                if(name.equals("   No Data Found"))
                {

                }else
                {
                    PersonalMeal.get_name(name);
                    try
                    {
                        Intent page = new Intent(ListOfMemberName.this,PersonalMeal.class);
                        startActivity(page);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

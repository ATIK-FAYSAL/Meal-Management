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

public class update_meal_code extends AppCompatActivity
{
    ListView list;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_meal);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SetNameListView();
    }


    private void SetNameListView()
    {
        final UpdatePersonMeal update = new UpdatePersonMeal();
        member_database member = new member_database(this);
        ArrayList<String> member_name = new ArrayList<>();
        Cursor value;
        value = member.GetAllMemberName();
        while(value.moveToNext())member_name.add(value.getString(0));
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,R.layout.all_member_textview,member_name);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = list.getItemAtPosition(position).toString();
                UpdatePersonMeal.get_name(name);
                Intent page = new Intent(update_meal_code.this,UpdatePersonMeal.class);
                startActivity(page);
            }
        });
    }

}

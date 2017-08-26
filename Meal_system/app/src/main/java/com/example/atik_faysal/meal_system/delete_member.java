package com.example.atik_faysal.meal_system;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class delete_member extends AppCompatActivity
{
    ListView list;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_member);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        set_name_list();
    }

    private void set_name_list()
    {
        final member_database database = new member_database(this);
        Cursor value = database.GetAllMemberName();
        ArrayList<String> name_array = new ArrayList<>();
        while(value.moveToNext())name_array.add(value.getString(0));
        if(name_array.isEmpty())name_array.add("    No data found");
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,R.layout.delete_member_textview,name_array);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String)list.getItemAtPosition(position);
                if(name.equals("    No data found"))Toast.makeText(delete_member.this,"No data found",Toast.LENGTH_LONG).show();
                else deleteMember(name);
            }
        });
    }

    private void deleteMember(String name)
    {
        final String Name = name;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final member_database database = new member_database(this);
        builder.setTitle("Delete");
        builder.setMessage("Delete this Member ?");
        View view = LayoutInflater.from(delete_member.this).inflate(R.layout.delete_alert_dialog,null);
        Button delete = (Button)view.findViewById(R.id.delete);
        Button cancel = (Button)view.findViewById(R.id.cancel);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = database.delete_member(Name);
                Toast.makeText(delete_member.this,str,Toast.LENGTH_LONG).show();
                set_name_list();
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}

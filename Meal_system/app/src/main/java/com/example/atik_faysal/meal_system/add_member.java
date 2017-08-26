package com.example.atik_faysal.meal_system;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;

public class add_member extends AppCompatActivity
{
    String name,phone,email,address,taka,parents_phone;
    Toolbar toolbar ;
    EditText e_name,e_email,e_phone,e_address,e_taka,e_p_phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        e_name =(EditText) findViewById(R.id.e_name);
        e_email = (EditText)findViewById(R.id.e_email);
        e_phone = (EditText)findViewById(R.id.e_phone);
        e_address = (EditText)findViewById(R.id.e_address);
        e_taka = (EditText)findViewById(R.id.e_taka);
        e_p_phone = (EditText)findViewById(R.id.e_parents_phone);
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
            SetErrorSign();
            save_all_information();
        }
        return true;
    }


    private void save_all_information()
    {
        name = e_name.getText().toString();
        phone = e_phone.getText().toString();
        email = e_email.getText().toString();
        address = e_address.getText().toString();
        taka = e_taka.getText().toString();
        parents_phone = e_p_phone.getText().toString();
        boolean flag = true;
        if(name.isEmpty())flag = false;
        if(phone.isEmpty())flag = false;
        if(email.isEmpty())flag = false;
        if(address.isEmpty())flag = false;
        if(taka.isEmpty())flag = false;
        if(parents_phone.isEmpty())flag = false;
        for(int i=0;i<name.length();i++)
        {
            if((name.charAt(i)>='A'&&name.charAt(i)<='Z')||(name.charAt(i)>='a'&&name.charAt(i)<='z')||name.charAt(i)==' '||name.charAt(i)=='-'||name.charAt(i)=='.')
            {

            }else
            {
                flag = false;break;
            }
        }
        if((phone.length()==11||phone.length()==14)&&(parents_phone.length()==11||parents_phone.length()==14))
        {

        }else flag = false;
        boolean check ;
        check = CheckAllName(name);
        if(flag==true&&check==true)
        {
            String str;
            setProgressDialog();
            member_database member = new member_database(this);
            str = member.add_member_information(name,email,phone,address,taka,parents_phone);
            Toast.makeText(add_member.this,str,Toast.LENGTH_LONG).show();
        }
        else if(check==false)SetAlertDialog();
        else setAlertDialog();
    }
    protected void setAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(add_member.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please insert valid data\n\nFor more information please click on HELP button");
        alertDialog.setPositiveButton("HELP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent page = new Intent(add_member.this,HelpCenter.class);
                startActivity(page);
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
    protected void setProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(add_member.this,"Please Wait","Data inserting...",true);
        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    progressDialog.dismiss();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private boolean CheckAllName(String name)
    {
        Vector<String> Name = new Vector<>();
        Cursor value;
        member_database member = new member_database(this);
        value = member.GetAllMemberName();
        while(value.moveToNext())Name.add(value.getString(0));
        if(Name.contains(name))return false;
        else return true;
    }
    private void SetAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(add_member.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("This Name is already exist. Please try again another Name !!");
        alertDialog.setPositiveButton("ok",null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }



    private void SetErrorSign()
    {
        e_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                boolean flag = true;
                name = e_name.getText().toString();
                for(int i=0;i<name.length();i++)
                {
                    if((name.charAt(i)>='A'&&name.charAt(i)<='Z')||(name.charAt(i)>='a'&&name.charAt(i)<='z')||name.charAt(i)==' '||name.charAt(i)=='-'||name.charAt(i)=='.')
                    {

                    }else
                    {
                        flag = false;break;
                    }
                }
                if(flag==false||e_name.getText().toString().isEmpty())e_name.setError("Please Enter Valid Name");
            }
        });

        e_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                phone = e_phone.getText().toString();
                if(phone.length()<=14)
                {
                    if(phone.length()==11||phone.length()==14)
                    {

                    }
                    else e_phone.setError("Enter Valid phone number");
                }else e_phone.setError("Error");
                if(phone.isEmpty()) e_phone.setError("Enter Phone number");
            }
        });
        e_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email = e_email.getText().toString();
                if(email.isEmpty())e_email.setError("Enter Email");
            }
        });
        e_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                address = e_address.getText().toString();
                if(address.isEmpty())e_address.setError("Enter Address");
            }
        });
        e_taka.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                taka = e_taka.getText().toString();
                if(taka.isEmpty()) e_taka.setError("Enter Taka");
            }
        });
        e_p_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                parents_phone = e_p_phone.getText().toString();
                if(parents_phone.isEmpty()) e_p_phone.setError("Parents phone field is Empty");
                parents_phone = e_p_phone.getText().toString();
                if(parents_phone.length()<=14)
                {
                    if(parents_phone.length()==11||parents_phone.length()==14)
                    {

                    }
                    else e_p_phone.setError("Enter Valid phone number");
                }else e_p_phone.setError("Error");
            }
        });
    }


}

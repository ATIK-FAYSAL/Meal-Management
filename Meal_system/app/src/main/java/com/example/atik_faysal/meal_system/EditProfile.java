package com.example.atik_faysal.meal_system;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class EditProfile extends AppCompatActivity
{
    Toolbar toolbar;
    static String Username;
    TextView e_username,e_name;
    EditText e_phone,e_email,e_address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        e_username = (TextView)findViewById(R.id.e_username);
        e_name = (TextView)findViewById(R.id.e_name);
        e_phone = (EditText)findViewById(R.id.e_phone);
        e_email = (EditText)findViewById(R.id.e_email);
        e_address = (EditText)findViewById(R.id.e_address);
        SetAllInformation();
    }

    public static void getUserName(String username)
    {
        Username = username;
    }

    private void SetAllInformation()
    {
        manager_database manager = new manager_database(this);
        Cursor value;
        value = manager.log_in(Username);
        ArrayList<String>maanager_info = new ArrayList<>();
        while(value.moveToNext())
        {
            maanager_info.add(value.getString(0));
            maanager_info.add(value.getString(1));
            maanager_info.add(value.getString(2));
            maanager_info.add(value.getString(3));
            maanager_info.add(value.getString(4));
        }
        e_username.setText(maanager_info.get(0));
        e_name.setText(maanager_info.get(1));
        e_phone.setText(maanager_info.get(2));
        e_email.setText(maanager_info.get(3));
        e_address.setText(maanager_info.get(4));
    }

    public void UpdateManagerInfo(View view)
    {
        String phone,email,address;
        phone = e_phone.getText().toString();
        email = e_email.getText().toString();
        address = e_address.getText().toString();
        boolean flag = checkData(phone,email);
        if(flag==true)
        {
            setProgressDialog();
            manager_database manager = new manager_database(this);
            String str = manager.UpdateInformation(e_username.getText().toString(),phone,email,address);
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }
        else
        {
            SetErrorSign();
            setAlertDialog();
        }
    }

    protected boolean checkData(String phone,String email)
    {
        boolean flag = true;
        if(phone.length()==14||phone.length()==11)
        {

        }
        else flag = false;
        if(email.length()>35||flag==false||email.length()<13)return false;
        else return true;
    }

    protected void setProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(EditProfile.this,"Please Wait","Data inserting...",true);
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


    protected void setAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(EditProfile.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please insert valid data\n\nFor more information please click on HELP button");
        alertDialog.setPositiveButton("HELP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    protected void SetErrorSign()
    {
        final String phone,email;
        phone = e_phone.getText().toString();
        email = e_email.getText().toString();
        e_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(phone.length()==11||phone.length()==14)
                {

                }else e_phone.setError("Enter Valid Phone number");
            }
        });
        e_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //email = e_email.getText().toString();
                if(email.length()>35||email.length()<13)e_email.setError("Enter valid Email");
            }
        });
    }
}

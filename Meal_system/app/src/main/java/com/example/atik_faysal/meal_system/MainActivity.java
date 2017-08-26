package com.example.atik_faysal.meal_system;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher,CompoundButton.OnCheckedChangeListener
{
    Toolbar toolbar;
    private String username,password;
    EditText e_username,e_password;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static String u_name="atik",u_password="123",u_checkbox = "remember",pref = "pref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        e_username = (EditText)findViewById(R.id.e_username);
        e_password = (EditText)findViewById(R.id.e_password);
        checkBox = (CheckBox)findViewById(R.id.c_remember_me);
        initialize();
        createAccount();
    }
    private void createAccount()
    {
        Button create = (Button)findViewById(R.id.b_newAccount);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page = new Intent(MainActivity.this,manager_info.class);
                startActivity(page);
            }
        });
    }

    private void initialize()
    {
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(sharedPreferences.getBoolean(u_checkbox,false))checkBox.setChecked(true);
        else checkBox.setChecked(false);
        e_username.setText(sharedPreferences.getString(u_name,""));
        e_password.setText(sharedPreferences.getString(u_password,""));
        e_username.addTextChangedListener(this);
        e_password.addTextChangedListener(this);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_account_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id;
        res_id = item.getItemId();
        if(res_id==R.id.helpCenter)
        {
            Intent page = new Intent(MainActivity.this,HelpCenter.class);
            startActivity(page);
        }
        return true;
    }
    public void log_in(View view)
    {
        manager_database database = new manager_database(this);
        username = e_username.getText().toString();
        password = e_password.getText().toString();
        Cursor value ;
        value = database.log_in(username);
        String get_username="",get_password="";
        try
        {
            while(value.moveToNext())
            {
                get_username = value.getString(0);
                get_password = value.getString(5);
            }
            setProgressDialog();
            if(get_username.equals(username)&&get_password.equals(password))
            {
                EditProfile.getUserName(username);
                ChangePassword.getUserName(username);
                Feedback.getUserName(username);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            Thread.sleep(2000);
                            Intent page = new Intent(MainActivity.this,home_page.class);
                            startActivity(page);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            else SetAlertDialog();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        set_data();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        set_data();
    }
    private void set_data()
    {
        if(checkBox.isChecked())
        {
            editor.putString(u_name,e_username.getText().toString().trim());
            editor.putString(u_password,e_password.getText().toString().trim());
            editor.putBoolean(u_checkbox,true);
            editor.apply();
        }
        else
        {
            editor.putBoolean(u_checkbox,false);
            editor.remove(u_name);
            editor.remove(u_password);
            editor.apply();
        }
    }

    protected void SetAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Log in Failed .Please try again with correct Username and Password !!");
        alertDialog.setPositiveButton("ok",null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    protected void setProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this,"Please Wait","Loging...",true);
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
}

package com.example.atik_faysal.meal_system;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.Vector;

public class manager_info extends AppCompatActivity
{
    Toolbar toolbar;
    private EditText e_username,e_name,e_phone,e_email,e_address,e_password,e_ConPassword;
    public String username,name,phone,email,address,password,conPassword;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountmanager);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        e_username = (EditText)findViewById(R.id.e_username);
        e_name = (EditText)findViewById(R.id.e_name);
        e_phone = (EditText)findViewById(R.id.e_phone);
        e_email = (EditText)findViewById(R.id.e_email);
        e_address = (EditText)findViewById(R.id.e_address);
        e_password = (EditText)findViewById(R.id.e_password);
        e_ConPassword = (EditText)findViewById(R.id.e_conPassword);
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
            network_class network = new network_class(this);
            if(network.check_internet())
            {
                SetErrorSign();
                check_data();
            }else checkInternet();
        }
        return true;
    }
    private void check_data()
    {
        manager_database SqlDatabase = new manager_database(this);
        firebase_database database = new firebase_database(this);
        username = e_username.getText().toString();
        name = e_name.getText().toString();
        phone = e_phone.getText().toString();
        email = e_email.getText().toString();
        address = e_address.getText().toString();
        password = e_password.getText().toString();
        conPassword = e_ConPassword.getText().toString();
        boolean flag = true;
        if(username.isEmpty())flag = false;
        if(name.isEmpty())flag = false;
        if(phone.isEmpty())flag = false;
        if(email.isEmpty())flag = false;
        if(address.isEmpty())flag = false;
        if(password.isEmpty())flag = false;
        if(conPassword.isEmpty())flag = false;
        for(int i=0;i<name.length();i++)
        {
            if((name.charAt(i)>='A'&&name.charAt(i)<='Z')||(name.charAt(i)>='a'&&name.charAt(i)<='z')||name.charAt(i)==' '||name.charAt(i)=='-')
            {

            }
            else
            {
                flag = false;
                break;
            }
        }
        if(phone.length()==11||phone.length()==14)
        {

        }
        else flag = false;
        if(password.length()<6||password.length()>25)flag = false;
        if(password.equals(conPassword))
        {

        }
        else flag = false;
        boolean check = check_manager_username(username);
        if(flag==true&&check==true)
        {
            setProgressDialog();
            database.execute(username,name,phone,email,address,password);
            SqlDatabase.add_manager_information(username,name,phone,email,address,password);
        }
        else if(check==false)SetAlertDialog();
        else setAlertDialog();
    }

    private boolean check_manager_username(String username)
    {
        Vector<String> name = new Vector<>();
        Cursor value ;
        manager_database man = new manager_database(this);
        value = man.get_all_name();
        while(value.moveToNext())name.add(value.getString(0));
        if(name.contains(username))return false;
        else return true;
    }

    private void setAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(manager_info.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please insert valid data\n\nFor more information please click on HELP button");
        alertDialog.setPositiveButton("HELP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent page = new Intent(manager_info.this,HelpCenter.class);
                startActivity(page);
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
    private void SetAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(manager_info.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("This Username is already exist. Please try again another Username !!");
        alertDialog.setPositiveButton("ok", null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }



    private void SetErrorSign()
    {
        e_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                username = e_username.getText().toString();
                if(username.isEmpty())e_username.setError("Enter UserName");
            }
        });
        e_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                name = e_name.getText().toString();
                boolean flag = true;
                for(int i=0;i<name.length();i++)
                {
                    if((name.charAt(i)>='A'&&name.charAt(i)<='Z')||(name.charAt(i)>='a'&&name.charAt(i)<='z')||name.charAt(i)==' '||name.charAt(i)=='-')
                    {

                    }
                    else
                    {
                        flag = false;
                        break;
                    }
                }
                if(name.isEmpty()||flag==false)e_name.setError("Enter Name");
            }
        });
        e_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                phone = e_phone.getText().toString();
                boolean flag = true;
                if(phone.isEmpty())flag = false;
                if(phone.length()==11||phone.length()==14)
                {

                }
                else flag = false;
                if(flag==false)e_phone.setError("Enter valid phone number");
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
        e_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password = e_password.getText().toString();
                if(password.length()>25||password.length()<6)e_password.setError("Invalid Password");
            }
        });
        e_ConPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                conPassword = e_ConPassword.getText().toString();
                password = e_password.getText().toString();
                if(conPassword.equals(password))
                {

                }
                else e_ConPassword.setError("Password Does Not Match");
            }
        });
    }


    private void setProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(manager_info.this,"Please Wait","Data inserting...",true);
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

    private void checkInternet()
    {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Please Check your Internet Connection !!");
        builder.setPositiveButton("ok",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

package com.example.atik_faysal.meal_system;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity
{
    Toolbar toolbar;
    EditText e_oldPass,e_nPass,e_confPass;
    static String Username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        e_oldPass = (EditText)findViewById(R.id.e_oldPass);
        e_nPass = (EditText)findViewById(R.id.e_newPass);
        e_confPass = (EditText)findViewById(R.id.e_confPass);
    }


    public static void getUserName(String username)
    {
        Username = username;
    }

    public void  Change_Password(View view)
    {
        String oldPass,newPass,confPass;
        oldPass = e_oldPass.getText().toString();
        newPass = e_nPass.getText().toString();
        confPass = e_confPass.getText().toString();
        if(newPass.equals(confPass))
        {
           if(newPass.length()<=35&&newPass.length()>=6)
           {
               String str ;
               manager_database manager = new manager_database(this);
               str = manager.changePassword(Username,oldPass,newPass);
               if(str.equals("Error"))SetAlertDialog();
               else Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
           }else setErrorSign();
        }else setErrorSign();
    }


    private void SetAlertDialog()
    {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(ChangePassword.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Password Does Not Match!!");
        alertDialog.setPositiveButton("ok",null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    protected void setErrorSign()
    {
        String newPass,confPass;
        newPass = e_nPass.getText().toString();
        confPass = e_confPass.getText().toString();
        if(newPass.equals(confPass))
        {

        }else e_confPass.setError("Password not Match");
        if(newPass.length()<6||newPass.length()>25)
        {
            if(newPass.length()<6)e_nPass.setError("Password too short");
            else if(newPass.length()>25)e_nPass.setError("Password cross limit");
        }
    }
}

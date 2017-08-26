package com.example.atik_faysal.meal_system;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseException;

public class firebase_database extends AsyncTask<String,Void,String>
{
    Context context;
    firebase_database(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        final ProgressDialog progressDialog = ProgressDialog.show(context,"Please Wait","Data inserting...",true);
        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    @Override
    protected String doInBackground(String... params) {
        String username,name,phone,email,address,password;
        username = params[0];
        name = params[1];
        phone = params[2];
        email = params[3];
        address = params[4];
        password = params[5];
        try
        {
            Firebase firebase = new Firebase("https://meal-system.firebaseio.com/user");
            firebase.child(username).child("name").setValue(name);
            firebase.child(username).child("phone").setValue(phone);
            firebase.child(username).child("email").setValue(email);
            firebase.child(username).child("address").setValue(address);
            firebase.child(username).child("password").setValue(password);
        }catch (FirebaseException e)
        {
            e.printStackTrace();
        }
        return "value inserted";
    }

    @Override
    protected void onPostExecute(String s) {
      Toast.makeText(context,"Value inserted ,you can log in now",Toast.LENGTH_LONG).show();
    }
}

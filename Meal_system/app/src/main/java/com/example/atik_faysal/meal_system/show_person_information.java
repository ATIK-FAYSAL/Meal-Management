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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Vector;


public class show_person_information extends AppCompatActivity {
    Toolbar toolbar;
    public static String Name;
    EditText e_email, e_phone, e_address, e_parent_phone,e_number;
    TextView e_taka,e_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_person_information);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        e_name = (TextView) findViewById(R.id.t_name);
        e_email = (EditText) findViewById(R.id.t_email);
        e_phone = (EditText) findViewById(R.id.t_phone);
        e_address = (EditText) findViewById(R.id.t_address);
        e_taka = (TextView) findViewById(R.id.t_taka);
        e_number = (EditText)findViewById(R.id.e_number);
        e_parent_phone = (EditText) findViewById(R.id.t_parent_phone);
        get_all_information();
    }

    public static void get_name(String name) {
        Name = name;
    }

    private void get_all_information() {
        ArrayList<String> information_list = new ArrayList<>();
        Cursor value;
        if (Name.equals("")) {

        } else {
            member_database member = new member_database(this);
            value = member.GetPersonInformation(Name);
            while (value.moveToNext()) {
                information_list.add(value.getString(0));
                information_list.add(value.getString(1));
                information_list.add(value.getString(2));
                information_list.add(value.getString(3));
                information_list.add(value.getString(4));
                information_list.add(value.getString(5));
            }
        }
        e_name.setText(Name);
        e_email.setText(information_list.get(1));
        e_phone.setText(information_list.get(2));
        e_taka.setText(information_list.get(4));
        e_address.setText(information_list.get(3));
        e_parent_phone.setText(information_list.get(5));
    }

    public void update_member_data(View view) {
        member_database member = new member_database(this);
        String  email, phone, taka, address, parents_phone,in_taka;
        email = e_email.getText().toString();
        phone = e_phone.getText().toString();
        taka = e_taka.getText().toString();
        address = e_address.getText().toString();
        in_taka = e_number.getText().toString();
        int total=0,Taka1,Taka2;
        try
        {
            Taka1 = Integer.parseInt(taka);
            Taka2 = Integer.parseInt(in_taka);
            total = Taka1+Taka2;
        }catch (NumberFormatException e)
        {
            Toast.makeText(this,"Please input valid data",Toast.LENGTH_LONG).show();
        }
        parents_phone = e_parent_phone.getText().toString();
        boolean flag = true;
        if (phone.isEmpty()) flag = false;
        if (email.isEmpty()) flag = false;
        if (address.isEmpty()) flag = false;
        if (taka.isEmpty()) flag = false;
        if (parents_phone.isEmpty()) flag = false;
        if ((phone.length() == 11 || phone.length() == 14) && (parents_phone.length() == 11 || parents_phone.length() == 14)) {

        } else flag = false;
        if (flag == true) {
            setProgressDialog();
            String str = member.update_member_data(Name,email,phone,address,total,parents_phone);
            Toast.makeText(show_person_information.this, str, Toast.LENGTH_LONG).show();
        }
        else setAlertDialog();
    }


    private void setAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(show_person_information.this);
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
    private void setProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(show_person_information.this,"Please Wait","Data Updating...",true);
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

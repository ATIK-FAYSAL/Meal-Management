package com.example.atik_faysal.meal_system;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class home_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String txt_month,txt_year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_profile) {
            Intent page = new Intent(home_page.this,EditProfile.class);
            startActivity(page);
        }
        else if (id == R.id.change_pass) {
            Intent page = new Intent(home_page.this,ChangePassword.class);
            startActivity(page);
        }
        else if(id==R.id.report)
        {
            setReportAlertdialog();
        }
        else if (id == R.id.logout) {
            SetProgressDialog();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        Thread.sleep(3000);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        closeActivity(home_page.this,MainActivity.class);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }).start();
        }
        else if (id == R.id.share) {
            Intent page = new Intent(Intent.ACTION_SEND);
            page.setType("text/plain");
            startActivity(Intent.createChooser(page,"Share Using"));
        }
        else if (id == R.id.feedback) {
            try
            {
                Intent page = new Intent(home_page.this,Feedback.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (id == R.id.delete) {
            setDeleteAlertDialog();
        }
        else if(id==R.id.CurrnetStatus)
        {
            try
            {
                Intent page = new Intent(home_page.this,Current_Status.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void button(View v)
    {
        if(v.getId()==R.id.b_addmember)
        {
            try
            {
                Intent page = new Intent(home_page.this,add_member.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.b_inputmeal)
        {
            try
            {
                Intent page = new Intent(home_page.this,insert_meal_code.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.b_updatemeal)
        {
            try
            {
                Intent page = new Intent(home_page.this,update_meal_code.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.b_monthlyreport)
        {
            try
            {
                Intent page = new Intent(home_page.this,MonthlyReport.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.b_memberlist)
        {
            try
            {
                Intent page = new Intent(home_page.this,see_all_member.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.b_dailyreport)
        {
            try
            {
                Intent page = new Intent(home_page.this,DailyReport.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.b_deletemember)
        {
            try
            {
                Intent page = new Intent(home_page.this,delete_member.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.b_personmeal)
        {
            try
            {
                Intent page = new Intent(home_page.this,ListOfMemberName.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.b_dailybazar)
        {
            try
            {
                Intent page = new Intent(home_page.this,bazar_code.class);
                startActivity(page);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private static void closeActivity(Activity context,Class<?>new_class)
    {
        Intent intent = new Intent(context,new_class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        context.finish();
    }

    protected void SetProgressDialog()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(home_page.this,"Please Wait","Log out...",true);
        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(3000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }



    private void setReportAlertdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(home_page.this).inflate(R.layout.show_report_dialog, null);
        Spinner month = (Spinner) view.findViewById(R.id.s_month);
        Spinner year = (Spinner) view.findViewById(R.id.s_year);
        Button show = (Button) view.findViewById(R.id.save);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        month.setAdapter(adapter);
        year.setAdapter(adapter1);
        final ReportDatabase report = new ReportDatabase(this);
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_month = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        builder.setView(view);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowReport.getMonthYear(txt_month,txt_year);
                Intent page = new Intent(home_page.this,ShowReport.class);
                startActivity(page);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void setDeleteAlertDialog()
    {
        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setTitle("Delete ");
        View view = LayoutInflater.from(home_page.this).inflate(R.layout.delete_dialog,null);
        final CheckBox checkBox1,checkBox2,checkBox3;
        builder.setView(view);
        checkBox1 = (CheckBox)view.findViewById(R.id.c_deleteInfo);
        checkBox2 = (CheckBox)view.findViewById(R.id.c_deleteReport);
        checkBox3 = (CheckBox)view.findViewById(R.id.c_deleteAll);
        Button button = (Button)view.findViewById(R.id.b_delete);
        if(checkBox3.isChecked())
        {
            checkBox1.setChecked(false);
            checkBox2.setChecked(false);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox1.isChecked()||checkBox2.isChecked()||checkBox3.isChecked())
                {
                    if(checkBox3.isChecked()) deleteAllRecord();
                    if(checkBox1.isChecked())deleteTaka();
                    if(checkBox2.isChecked())
                    {
                        ReportDatabase report = new ReportDatabase(home_page.this);
                        report.deleteReport();
                        report.deleteAll();
                    }
                    Toast.makeText(home_page.this,"Information delete successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    protected void deleteTaka()
    {
        member_database member = new member_database(this);
        Cursor value;
        ArrayList<String>member_name = new ArrayList<>();
        value = member.GetAllMemberName();
        while(value.moveToNext())member_name.add(getString(0));
        for(int i=0;i<member_name.size();i++)member.deleteMemberTaka(member_name.get(i),0);
        bazar_database bazar = new bazar_database(this);
        bazar.deleteReport();
    }
    private void deleteAllRecord()
    {
        member_database member = new member_database(this);
        ReportDatabase report = new ReportDatabase(this);
        bazar_database bazar = new bazar_database(this);
        MealDatabase meal = new MealDatabase(this);
        member.deleteAll();
        report.deleteAll();
        report.deleteReport();
        bazar.deleteReport();
        meal.deleteAll();
    }
}

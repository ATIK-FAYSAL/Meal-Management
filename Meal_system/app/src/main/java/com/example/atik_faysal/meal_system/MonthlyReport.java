package com.example.atik_faysal.meal_system;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MonthlyReport extends AppCompatActivity
{
    ArrayList<String>MemberName;
    ArrayList<Integer>MemberMeal;
    ArrayList<Integer>MemberTaka;
    ArrayList<Float>MemberCost;
    ArrayList<Float>memberCost;
    List<ReportClass> member_report;
    ReportMyAdapter Adapter;
    String txt_month,txt_year;
    ListView list;
    public float meal_rate;
    Toolbar toolbar;
    TextView txt_totalTaka,txt_totalBazar,txt_totalMeal,txt_mealRate;
    int totalCost,totalMemberTaka,TotalMeal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_report);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt_totalTaka = (TextView)findViewById(R.id.txt_totalTaka);
        txt_totalBazar = (TextView)findViewById(R.id.txt_totalBazar);
        txt_totalMeal = (TextView)findViewById(R.id.txt_totalMeal);
        txt_mealRate = (TextView)findViewById(R.id.txt_mealRate);
        InformationTextView();
        GetAllInformation();
        Calculate();
    }

    private void InformationTextView()
    {
        try
        {
            member_database member = new member_database(this);
            bazar_database bazar = new bazar_database(this);
            MealDatabase meal = new MealDatabase(this);
            totalCost = bazar.sum_bazar();
            totalMemberTaka = member.sum_of_member_taka();
            TotalMeal = meal.OneMonthMeal();
            String str;
            if(totalMemberTaka==0||totalCost==0||TotalMeal==0)
            {
                if(totalCost==0)setAlertDialogCost();
                else if(TotalMeal==0)setAlertDialogMeal();
            }
            else
            {
                meal_rate = (float)totalCost/TotalMeal;
                str = String.format("%.4f",meal_rate);
                txt_totalTaka.setText(totalMemberTaka+"/=");
                txt_totalBazar.setText(totalCost+"/=");
                txt_totalMeal.setText(TotalMeal+"/=");
                txt_mealRate.setText(str+"/=");
            }
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }catch (ArithmeticException e)
        {
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void GetAllInformation()
    {
        member_database member = new member_database(this);
        MemberTaka = new ArrayList<>();
        MemberName = new ArrayList<>();
        Cursor NameValue;
        NameValue = member.GetNameTaka();
        while(NameValue.moveToNext())
        {
            int taka;
            MemberName.add(NameValue.getString(0));
            try
            {
                taka = Integer.parseInt(NameValue.getString(1));
                MemberTaka.add(taka);
            }catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void Calculate()
    {
        memberCost = new ArrayList<>();
        MealDatabase meal = new MealDatabase(this);
        member_report = new ArrayList<>();
        MemberCost = new ArrayList<>();
        list = (ListView)findViewById(R.id.list);
        int mealValue;
        MemberMeal = new ArrayList<>();
        int i=0;
        while(i<MemberName.size())
        {
            mealValue = meal.GetAllPersonalMeal(MemberName.get(i));
            MemberMeal.add(mealValue);
            i++;
        }
        for(i=0;i<MemberName.size();i++)
        {
            float value,taka;
            value = MemberMeal.get(i)*meal_rate;
            taka = MemberTaka.get(i)-value;
            MemberCost.add(value);
            memberCost.add(taka);
        }
        for(i=0;i<MemberName.size();i++)
        {
            String cost,due;
            cost = String.format("%.3f",MemberCost.get(i));
            due =  String.format("%.3f",memberCost.get(i));
            member_report.add(new ReportClass(MemberName.get(i),MemberMeal.get(i)+"",MemberTaka.get(i)+"",cost+"",due+""));
        }
        Adapter = new ReportMyAdapter(this,member_report);
        list.setAdapter(Adapter);
    }


    protected void setAlertDialogCost()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Total Cost is 0 !!\nPlease insert some value.");
        builder.setPositiveButton("ok",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void setAlertDialogMeal()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Total Meal is 0 !!\nPlease insert some value.");
        builder.setPositiveButton("ok",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setSaveAlertdialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(MonthlyReport.this).inflate(R.layout.save_layout,null);
        Spinner month = (Spinner)view.findViewById(R.id.s_month);
        Spinner year = (Spinner)view.findViewById(R.id.s_year);
        Button save = (Button)view.findViewById(R.id.save);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.month,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence>adapter1 = ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_item);
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
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean flag = CheckDate(txt_month+"-"+txt_year);
                    if(flag==true)
                    {
                        for(int i=0;i<MemberName.size();i++)report.InsertReport(txt_month+"-"+txt_year,MemberName.get(i),MemberMeal.get(i)+"",MemberTaka.get(i)+"",MemberCost.get(i)+"",memberCost.get(i)+"");
                        report.saveMealRate(txt_month+"-"+txt_year,totalMemberTaka+"",totalCost+"",TotalMeal+"",meal_rate+"");
                        Toast.makeText(MonthlyReport.this,"Your Report is Saved",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(MonthlyReport.this,"This Report is already Saved",Toast.LENGTH_LONG).show();
                }
            });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
            setSaveAlertdialog();
        }
        return true;
    }
    private boolean CheckDate(String date)
    {
        Cursor value;
        ArrayList<String>date_list = new ArrayList<>();
        ReportDatabase report = new ReportDatabase(this);
        value = report.CheckDate();
        while(value.moveToNext())date_list.add(value.getString(0));
        if(date_list.contains(date))return false;
        else return true;
    }
}

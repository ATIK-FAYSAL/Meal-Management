package com.example.atik_faysal.meal_system;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Current_Status extends AppCompatActivity
{
    Toolbar toolbar;
    TextView t_cost,t_balance,t_remainder,t_meal_rate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_status);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t_cost = (TextView)findViewById(R.id.total_cost_text);
        t_balance = (TextView)findViewById(R.id.total_taka_text);
        t_remainder = (TextView)findViewById(R.id.remainder_text);
        t_meal_rate = (TextView)findViewById(R.id.meal_rate_text);
        set_data();
        InformationTextView();
    }

    private void set_data() {
        bazar_database database = new bazar_database(this);
        member_database member = new member_database(this);
        int total_cost, total_balance, Remainder;
        total_cost = database.sum_bazar();
        total_balance = member.sum_of_member_taka();
        String taka = total_cost + "";
        String Taka = total_balance + "/=";
        String remainder;
        Remainder = total_balance - total_cost;
        if (Remainder <= 0) t_remainder.setText(Remainder+"/=");
        else {
            remainder = Remainder + "/=";
            t_remainder.setText(remainder);
        }
        t_balance.setText(Taka);
        t_cost.setText(taka + "/=");
    }
    private void InformationTextView()
    {
        float meal_rate;
        try
        {
            bazar_database bazar = new bazar_database(this);
            MealDatabase meal = new MealDatabase(this);
            int totalCost,TotalMeal;
            totalCost = bazar.sum_bazar();
            TotalMeal = meal.OneMonthMeal();
            String str;
            meal_rate = (float)totalCost/TotalMeal;
            str = String.format("%.4f",meal_rate);
            t_meal_rate.setText(str+"/=");
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
}

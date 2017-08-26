package com.example.atik_faysal.meal_system;

/**
 * Created by user on 8/21/2017.
 */

public class ReportClass
{
    String name,meal,total_taka,total_cost,due;
    public ReportClass(String name,String meal,String total_taka,String total_cost,String due)
    {
        this.name = name;
        this.meal = meal;
        this.total_taka = total_taka;
        this.total_cost = total_cost;
        this.due = due;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getTotal_taka() {
        return total_taka;
    }

    public void setTotal_taka(String total_taka) {
        this.total_taka = total_taka;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }
}

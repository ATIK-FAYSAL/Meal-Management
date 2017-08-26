package com.example.atik_faysal.meal_system;

/**
 * Created by user on 8/20/2017.
 */

public class BazarListClass
{
    String date,name,taka;
    BazarListClass(String date,String name,String taka)
    {
        this.date = date;
        this.name = name;
        this.taka = taka;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaka() {
        return taka;
    }

    public void setTaka(String taka) {
        this.taka = taka;
    }
}

package com.example.atik_faysal.meal_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends BaseAdapter
{
    TextView txt_date,txt_name,txt_breakfast,txt_lunch,txt_dinner;
    View view;
    List<MemeberMealClass>MealList;
    Context context;
    public MyAdapter(Context context,List<MemeberMealClass>mealList)
    {
        this.context = context;
        this.MealList = mealList;
    }

    @Override
    public int getCount() {
        return MealList.size();
    }

    @Override
    public Object getItem(int position) {
        return MealList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.show_rows,parent,false);
        txt_date = (TextView)view.findViewById(R.id.txt_date);
        txt_name = (TextView)view.findViewById(R.id.txt_name);
        txt_breakfast = (TextView)view.findViewById(R.id.txt_breakfast);
        txt_lunch = (TextView)view.findViewById(R.id.txt_lunch);
        txt_dinner = (TextView)view.findViewById(R.id.txt_dinner);
        txt_date.setText(MealList.get(position).getDate());
        txt_name.setText(MealList.get(position).getName());
        txt_breakfast.setText(MealList.get(position).getBreakfast());
        txt_lunch.setText(MealList.get(position).getLunch());
        txt_dinner.setText(MealList.get(position).getDinner());

        return view;
    }
}

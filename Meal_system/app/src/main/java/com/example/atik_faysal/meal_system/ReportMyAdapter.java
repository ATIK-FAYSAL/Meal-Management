package com.example.atik_faysal.meal_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 8/21/2017.
 */

public class ReportMyAdapter extends BaseAdapter
{
    TextView txt_name,txt_meal,txt_taka,txt_cost,txt_due;
    public List<ReportClass>member_report;
    public ReportMyAdapter adapter;
    Context context;
    View view;
    public ReportMyAdapter(Context context,List<ReportClass> member_report)
    {
        this.context = context;
        this.member_report = member_report;
    }
    @Override
    public int getCount() {
        return member_report.size();
    }

    @Override
    public Object getItem(int position) {
        return member_report.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.report_show_rows,parent,false);
        txt_meal = (TextView)view.findViewById(R.id.txt_meal);
        txt_name = (TextView)view.findViewById(R.id.txt_name);
        txt_taka = (TextView)view.findViewById(R.id.txt_taka);
        txt_cost = (TextView)view.findViewById(R.id.txt_cost);
        txt_due = (TextView)view.findViewById(R.id.txt_due);
        txt_name.setText(member_report.get(position).getName());
        txt_meal.setText(member_report.get(position).getMeal());
        txt_taka.setText(member_report.get(position).getTotal_taka());
        txt_cost.setText(member_report.get(position).getTotal_cost());
        txt_due.setText(member_report.get(position).getDue());
        return view;
    }
}

package com.example.atik_faysal.meal_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BazarMyAdapter extends BaseAdapter {
    TextView txt_date,txt_name,txt_taka;
    List<BazarListClass> bazarList;
    Context context;
    View view;
    public BazarMyAdapter(Context context,List<BazarListClass>bazarList)
    {
        this.context = context;
        this.bazarList = bazarList;
    }

    @Override
    public int getCount() {
        return bazarList.size();
    }

    @Override
    public Object getItem(int position) {
        return bazarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.bazar_show_rows,parent,false);
        txt_date = (TextView)view.findViewById(R.id.txt_date);
        txt_name = (TextView)view.findViewById(R.id.txt_name);
        txt_taka = (TextView)view.findViewById(R.id.txt_taka);
        txt_date.setText(bazarList.get(position).getDate());
        txt_name.setText(bazarList.get(position).getName());
        txt_taka.setText(bazarList.get(position).getTaka());
        return view;
    }
}

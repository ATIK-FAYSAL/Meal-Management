package com.example.atik_faysal.meal_system;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;


public class HelpCenter extends Activity
{
    TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_center);
        text = (TextView)findViewById(R.id.text);
        setString();
    }
    protected void setString()
    {
        String str;
        String str1,str2,str3,str4,str5,str6,str7;
        str1 = "1.Name Can not contain special Character(@,#,*,_,:,)and digit ,without space.\n ";
        str2 = "2.User name also can not contain special Character(@,#,*,_,:,).\n";
        str3 = "3.Phone number can not contain alphabet or special Character(@,#,*,_,:,)\n";
        str4 = "4.You must be input taka.\n";
        str5 = "5.Only manager can Log in this app.\n";
        str6 = "6.Only manager Can add new member.\n";
        str7 = "7.Password must be in length (6-25).\n";
        str = str1+str2+str3+str4+str5+str6+str7;
        text.setText(str);
    }
}

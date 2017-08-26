package com.example.atik_faysal.meal_system;


import android.app.AlertDialog;
import android.database.Cursor;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Feedback extends AppCompatActivity
{
    RatingBar rate;
    TextView t_value;
    Toolbar toolbar;
    EditText e_feedback;
    static String Username;
    String Name,phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rate = (RatingBar)findViewById(R.id.r_feedback);
        t_value = (TextView)findViewById(R.id.t_value);
        e_feedback = (EditText)findViewById(R.id.e_fedback);
        Firebase.setAndroidContext(this);
        check_internet();
    }

    public static void getUserName(String username)
    {
        Username = username;
    }

    protected void check_internet()
    {
        network_class network = new network_class(this);
        if(network.check_internet())
        {
            setRating();
            countRate();
        }else checkInternet();
    }

    protected void countRate()
    {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rating");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long Count = dataSnapshot.getChildrenCount();
                t_value.setText(Count+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void setRating()
    {
        final Firebase firebase = new Firebase("https://meal-system.firebaseio.com/Rating");
        final String get_id = firebase.push().getKey();
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                firebase.child(get_id).setValue(rating);
                Toast.makeText(Feedback.this,"Rating : "+rating,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveValue(View view)
    {
        network_class network = new network_class(this);
        boolean flag = setFeedback();
        if(flag==true&&network.check_internet()) Toast.makeText(this,"Thanks for your Feedback",Toast.LENGTH_SHORT).show();
        else
        {
            if(!network.check_internet())check_internet();
            else
            {
                Toast.makeText(this,"Suggestion must be in 20-200 characters",Toast.LENGTH_SHORT).show();
                setError();
            }
        }
    }

    private boolean setFeedback()
    {
        boolean flag = true;
        String feedback = e_feedback.getText().toString();
        if(feedback.length()<=200&&feedback.length()>=20)
        {
            Firebase firebase = new Firebase("https://meal-system.firebaseio.com/feedback");
            String get_id = firebase.push().getKey();
            firebase.child(get_id).child("Feedback").setValue(feedback);
            getNamePhone();
            firebase.child(get_id).child("Name").setValue(Name);
            firebase.child(get_id).child("Phone").setValue(phone);
        }
        else flag=false;
        if(flag==true)return true;
        else return false;
    }

    private void getNamePhone()
    {
        manager_database manager = new manager_database(this);
        Cursor value;
        value = manager.GetNamePhone(Username);
        while(value.moveToNext())
        {
            Name = value.getString(0);
            phone = value.getString(1);
        }
    }


    protected void setError()
    {
        e_feedback.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String str = e_feedback.getText().toString();
                if(str.length()>200||str.length()<20)e_feedback.setError("Suggestion must be in 20-200 characters");
            }
        });
    }
    private void checkInternet()
    {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Please Check your Internet Connection !!");
        builder.setPositiveButton("ok",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

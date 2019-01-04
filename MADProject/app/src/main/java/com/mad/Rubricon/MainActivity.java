package com.mad.Rubricon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    public void logIn(View v)
    {
        Intent i = new Intent(this,StudentSignUp.class);
        startActivity(i);
    }
    public void teacherRegistration(View v)
    {
        Intent i = new Intent(this,TeacherSignUp.class);
        startActivity(i);
    }
    public void adminSignUp(View v)
    {
        Intent i = new Intent(this,AdminSignUp.class);
        startActivity(i);
    }
}


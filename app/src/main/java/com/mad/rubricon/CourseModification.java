package com.mad.rubricon;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CourseModification extends AppCompatActivity {
    EditText CourseCode;
    DBManager db;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signoutmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item:
                //Intent j = new Intent(this,Stats.class);
                Intent j = new Intent(this,Login.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_modification);

        CourseCode = (EditText) findViewById(R.id.CourseCode);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
        Toolbar toolbar = findViewById(R.id.toolbar_courses);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Course Modification");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
    }
    public void courseUpd(View v)
    {

        String Code = CourseCode.getText().toString();
        ContentValues c = db.getCourseData(Code);
        // Checking if no records found
        if (c == null) {
            showMessage("Error", "No records found for Course Code");
            clearText();
            return;
        }
        else
        {
            Intent i = new Intent(this,CourseUpdate.class);
            i.putExtra("CourseCode",CourseCode.getText().toString());
            startActivity(i);
        }

    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText() {
        CourseCode.setText("");
    }
}

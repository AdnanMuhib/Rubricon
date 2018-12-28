package com.example.tanzeel.Rubricon;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TeacherLogin extends AppCompatActivity {
    EditText Emails,Password;
    DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        Emails = (EditText) findViewById(R.id.emails);
        Password = (EditText) findViewById(R.id.password);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
    }
    public void TeacherLogin(View v)
    {
        if (Emails.getText().toString().trim().length() == 0 ||
                Password.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            return;
        }
        Boolean login = db.teacherLogin(Emails.getText().toString(),Password.getText().toString());
        if(login==true)
        {
            Cursor c = db.getTeacherCoursesList(Emails.getText().toString());
            // Checking if no records found
            if (c==null || c.getCount() <= 0) {
                showMessage("Error", "No Course Allocated!");
                return;
            }
            else {
                Intent i = new Intent(this, TeacherCourseList.class);
                i.putExtra("Email", Emails.getText().toString());
                startActivity(i);
                showMessage("Success", "Login Successfully");
                clearText();
            }
        }
        else
        {
            showMessage("Error", "Wrong Credentials");
            clearText();
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
        Emails.setText("");
        Password.setText("");

    }

}

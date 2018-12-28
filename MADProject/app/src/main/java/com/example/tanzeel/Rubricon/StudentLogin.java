package com.example.tanzeel.Rubricon;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class StudentLogin extends AppCompatActivity {
    EditText StudentReg,StudentPass;
    DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        StudentReg = (EditText) findViewById(R.id.RegNo);
        StudentPass = (EditText) findViewById(R.id.pass);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
    }
    public void studentLogin(View v)
    {
        if (StudentReg.getText().toString().trim().length() == 0 ||
                StudentPass.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            return;
        }
        Boolean login = db.studentLogin(StudentReg.getText().toString(),StudentPass.getText().toString());
        if(login==true)
        {
            showMessage("Success", "Login Successfully");
            Intent i = new Intent(this,StudentPanel.class);
            i.putExtra("RegNo",StudentReg.getText().toString());
            startActivity(i);
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
        StudentPass.setText("");
        StudentReg.setText("");
    }
}

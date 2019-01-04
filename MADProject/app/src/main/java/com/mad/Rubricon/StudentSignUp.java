package com.mad.Rubricon;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.regex.Pattern;

public class StudentSignUp extends AppCompatActivity {
    TextView DOB;
    DBManager db;
    EditText StdName, StdRegNo,StdEmail, StdCnic,StdPass;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        StdName = (EditText) findViewById(R.id.Name);
        StdRegNo = (EditText) findViewById(R.id.RegNo);
        StdEmail = (EditText) findViewById(R.id.email);
        StdCnic = (EditText) findViewById(R.id.StdCnic);
        StdPass = (EditText) findViewById(R.id.Pass);

        DOB = (TextView) findViewById(R.id.dob);
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        StudentSignUp.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                DOB.setText(date);
            }
        };
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
    }
    public void studentRegister(View v)
    {
        if (StdName.getText().toString().trim().length() == 0 ||
                StdRegNo.getText().toString().trim().length() == 0 ||
                StdEmail.getText().toString().trim().length() == 0 ||
                StdCnic.getText().toString().trim().length() == 0 ||
                StdPass.getText().toString().trim().length() == 0||
                DOB.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            return;
        }
        if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(StdEmail.getText().toString()).matches()))
        {
            showMessage("Error", "Please enter correct email");
            return;
        }

        boolean b = Pattern.matches("[0-9]{4}-[a-zA-Z]{2,}-[0-9]{2,}", StdRegNo.getText().toString());
        if(b==false)
        {
            showMessage("Error", "Please enter correct Registration No");
            return;
        }
        boolean cnic = Pattern.matches("[0-9]{5}-[0-9]{7}-[0-9]{1}", StdCnic.getText().toString());
        if(cnic==false)
        {
            showMessage("Error", "Please enter correct CNIC");
            return;
        }

        long result =  db.registerStudent( StdName.getText().toString(),StdEmail.getText().toString(), StdCnic.getText().toString(),DOB.getText().toString(),
                StdPass.getText().toString(), StdRegNo.getText().toString());
        if(result == -1)
        {
            showMessage("Error", "Record not added");
        }
        else
        {
            showMessage("Success", "Record added");
            clearText();}


    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText() {
        StdName.setText("");
        StdEmail.setText("");
        StdCnic.setText("");
        StdPass.setText("");
        StdRegNo.setText("");
        DOB.setText("");
    }


}

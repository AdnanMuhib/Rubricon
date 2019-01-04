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

public class AdminSignUp extends AppCompatActivity {
    TextView DOB;
    DBManager db;
    EditText name,cnic,email,pass;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);
        DOB = (TextView) findViewById(R.id.DOB);
        name = (EditText)findViewById(R.id.name);
        cnic = (EditText) findViewById(R.id.cnic);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AdminSignUp.this,
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
    public void AdminRegister(View v)
    {
        if (name.getText().toString().trim().length() == 0 ||
                cnic.getText().toString().trim().length() == 0 ||
                email.getText().toString().trim().length() == 0 ||
                pass.getText().toString().trim().length() == 0 ||
                DOB.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            return;
        }
        boolean b = Pattern.matches("[0-9]{5}-[0-9]{7}-[0-9]{1}", cnic.getText().toString());
        if(b==false)
        {
            showMessage("Error", "Please enter correct CNIC");
            return;
        }
        if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()))
        {
            showMessage("Error", "Please enter correct email");
            return;
        }

        long result =  db.registerAdmin( name.getText().toString(),email.getText().toString(), cnic.getText().toString(),DOB.getText().toString(),
                pass.getText().toString());
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
        name.setText("");
        cnic.setText("");
        email.setText("");
        pass.setText("");
        DOB.setText("");
    }


}

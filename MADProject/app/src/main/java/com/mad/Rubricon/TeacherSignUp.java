package com.mad.Rubricon;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.regex.Pattern;

public class TeacherSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView DOB;
    DBManager db;
    EditText TeacherName,TeacherEmail,TeacherCNIC, TeacherQual,TeacherDept,TeacherPass;
    Spinner Designation;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Designation = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.designation,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Designation.setAdapter(adapter);
        Designation.setOnItemSelectedListener(this);
        TeacherName = (EditText) findViewById(R.id.TeacherName);
        TeacherEmail = (EditText) findViewById(R.id.TeacherEmail);
        TeacherCNIC = (EditText) findViewById(R.id.TeacherCNIC);
        TeacherQual = (EditText) findViewById(R.id.TeacherQual);
        TeacherDept = (EditText) findViewById(R.id.TeacherDept);
        TeacherPass = (EditText) findViewById(R.id.TeacherPass);
        DOB = (TextView) findViewById(R.id.DOB);
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TeacherSignUp.this,
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void Register(View view) {
        if (TeacherName.getText().toString().trim().length() == 0 ||
                TeacherEmail.getText().toString().trim().length() == 0 ||
                TeacherCNIC.getText().toString().trim().length() == 0 ||
                TeacherQual.getText().toString().trim().length() == 0 ||
                TeacherDept.getText().toString().trim().length() == 0||
                TeacherPass.getText().toString().trim().length() == 0 ||
                DOB.getText().toString().trim().length() == 0 ||
                Designation.getSelectedItem().toString().trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            return;
        }
        if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(TeacherEmail.getText().toString()).matches()))
        {
            showMessage("Error", "Please enter correct email");
            return;
        }
        boolean b = Pattern.matches("[0-9]{5}-[0-9]{7}-[0-9]{1}", TeacherCNIC.getText().toString());
        if(b==false)
        {
            showMessage("Error", "Please enter correct CNIC");
            return;
        }


        long result =  db.registerTeacher( TeacherName.getText().toString(),TeacherEmail.getText().toString(), TeacherCNIC.getText().toString(),DOB.getText().toString(),
                TeacherPass.getText().toString(),Designation.getSelectedItem().toString(),TeacherDept.getText().toString(),TeacherQual.getText().toString());
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
        TeacherName.setText("");
        TeacherEmail.setText("");
        TeacherCNIC.setText("");
        TeacherQual.setText("");
        TeacherDept.setText("");
        TeacherPass.setText("");
        DOB.setText("");
    }

}
